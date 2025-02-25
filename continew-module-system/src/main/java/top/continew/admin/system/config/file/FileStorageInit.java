/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.system.config.file;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;
import top.continew.admin.system.enums.StorageTypeEnum;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.storage.client.LocalClient;
import top.continew.starter.storage.client.OssClient;
import top.continew.starter.storage.constant.StorageConstant;
import top.continew.starter.storage.dao.StorageDao;
import top.continew.starter.storage.manger.StorageManager;
import top.continew.starter.storage.model.req.StorageProperties;
import top.continew.starter.storage.strategy.LocalStorageStrategy;
import top.continew.starter.storage.strategy.OssStorageStrategy;
import top.continew.starter.storage.util.StorageUtils;
import top.continew.starter.web.util.SpringWebUtils;

import java.util.Map;

/**
 * 文件存储初始化
 *
 * @author echo
 * @author Charles7c
 * @since 2024/12/20 11:10
 */
@Component
public class FileStorageInit {

    /**
     * 加载文件存储
     *
     * @param map 存储配置
     */
    public void load(Map<String, String> map) {
        StorageManager.unload(StorageTypeEnum.OSS.name());
        StorageManager.unload(StorageTypeEnum.LOCAL.name());
        // 获取默认存储值并缓存
        String storageDefault = cacheDefaultStorage(map);
        if (StorageTypeEnum.LOCAL.name().equals(storageDefault)) {
            // 获取本地终端地址 和桶地址
            String localEndpoint = map.get("STORAGE_LOCAL_ENDPOINT");
            String localBucket = map.get("STORAGE_LOCAL_BUCKET");
            // 构建并加载本地存储配置
            StorageProperties localProperties = buildStorageProperties(StorageTypeEnum.LOCAL
                .name(), localBucket, storageDefault, localEndpoint);
            // 本地静态资源映射
            SpringWebUtils.registerResourceHandler(MapUtil.of(StorageUtils.createUriWithProtocol(localEndpoint)
                .getPath(), localBucket));
            StorageManager.load(localProperties
                .getCode(), new LocalStorageStrategy(new LocalClient(localProperties), SpringUtil
                    .getBean(StorageDao.class)));
        } else if (StorageTypeEnum.OSS.name().equals(storageDefault)) {
            // 构建并加载对象存储配置
            StorageProperties ossProperties = buildStorageProperties(StorageTypeEnum.OSS.name(), map
                .get("STORAGE_OSS_BUCKET"), storageDefault, map.get("STORAGE_OSS_ACCESS_KEY"), map
                    .get("STORAGE_OSS_SECRET_KEY"), map.get("STORAGE_OSS_ENDPOINT"), map.get("STORAGE_OSS_REGION"));
            StorageManager.load(ossProperties.getCode(), new OssStorageStrategy(new OssClient(ossProperties), SpringUtil
                .getBean(StorageDao.class)));
        }
    }

    /**
     * 卸载文件存储
     *
     * @param code 存储编码
     */
    public void unLoad(String code) {
        StorageManager.unload(code);
    }

    /**
     * 将默认存储值放入缓存
     *
     * @param map 存储配置
     * @return {@link String }
     */
    private String cacheDefaultStorage(Map<String, String> map) {
        String storageDefault = MapUtil.getStr(map, "STORAGE_DEFAULT");
        RedisUtils.set(StorageConstant.DEFAULT_KEY, storageDefault);
        return storageDefault;
    }

    /**
     * 构建本地存储配置属性
     *
     * @param code        存储码
     * @param bucketName  桶名称
     * @param defaultCode 默认存储码
     * @return {@link StorageProperties }
     */
    private StorageProperties buildStorageProperties(String code,
                                                     String bucketName,
                                                     String defaultCode,
                                                     String endpoint) {
        StorageProperties properties = new StorageProperties();
        properties.setCode(code);
        properties.setBucketName(bucketName);
        properties.setEndpoint(endpoint);
        properties.setIsDefault(code.equals(defaultCode));
        return properties;
    }

    /**
     * 构建对象存储配置属性
     *
     * @param code        存储码
     * @param bucketName  桶名称
     * @param defaultCode 默认存储码
     * @param accessKey   访问密钥
     * @param secretKey   秘密密钥
     * @param endpoint    端点
     * @param region      区域
     * @return {@link StorageProperties }
     */
    private StorageProperties buildStorageProperties(String code,
                                                     String bucketName,
                                                     String defaultCode,
                                                     String accessKey,
                                                     String secretKey,
                                                     String endpoint,
                                                     String region) {
        StorageProperties properties = buildStorageProperties(code, bucketName, defaultCode, endpoint);
        properties.setAccessKey(accessKey);
        properties.setSecretKey(secretKey);
        properties.setRegion(region);
        return properties;
    }
}
