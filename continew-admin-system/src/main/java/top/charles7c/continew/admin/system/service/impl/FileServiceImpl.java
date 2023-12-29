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

package top.charles7c.continew.admin.system.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.dromara.x.file.storage.core.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import top.charles7c.continew.admin.system.enums.StorageTypeEnum;
import top.charles7c.continew.admin.system.mapper.FileMapper;
import top.charles7c.continew.admin.system.model.entity.FileDO;
import top.charles7c.continew.admin.system.model.entity.StorageDO;
import top.charles7c.continew.admin.system.model.query.FileQuery;
import top.charles7c.continew.admin.system.model.req.FileReq;
import top.charles7c.continew.admin.system.model.resp.FileDetailResp;
import top.charles7c.continew.admin.system.model.resp.FileResp;
import top.charles7c.continew.admin.system.model.resp.StorageDetailResp;
import top.charles7c.continew.admin.system.service.FileService;
import top.charles7c.continew.admin.system.service.StorageService;
import top.charles7c.continew.starter.core.constant.StringConstants;
import top.charles7c.continew.starter.core.util.URLUtils;
import top.charles7c.continew.starter.core.util.validate.CheckUtils;
import top.charles7c.continew.starter.extension.crud.base.BaseServiceImpl;

/**
 * 文件业务实现
 *
 * @author Charles7c
 * @since 2023/12/23 10:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends BaseServiceImpl<FileMapper, FileDO, FileResp, FileDetailResp, FileQuery, FileReq>
    implements FileService {

    @Resource
    private StorageService storageService;
    private final FileStorageService fileStorageService;

    @Override
    public FileInfo upload(MultipartFile file, String storageCode) {
        StorageDO storage;
        if (StrUtil.isBlank(storageCode)) {
            storage = storageService.getDefaultStorage();
            CheckUtils.throwIfNull(storage, "请先指定默认存储库");
        } else {
            storage = storageService.getByCode(storageCode);
            CheckUtils.throwIfNotExists(storage, "StorageDO", "Code", storageCode);
        }
        UploadPretreatment uploadPretreatment = fileStorageService.of(file).setPlatform(storage.getCode());
        uploadPretreatment.setProgressMonitor(new ProgressListener() {
            @Override
            public void start() {
                log.info("开始上传");
            }

            @Override
            public void progress(long progressSize, long allSize) {
                log.info("已上传 [{}]，总大小 [{}]", progressSize, allSize);
            }

            @Override
            public void finish() {
                log.info("上传结束");
            }
        });
        // 处理本地存储文件 URL
        FileInfo fileInfo = uploadPretreatment.upload();
        fileInfo.setUrl(StorageTypeEnum.LOCAL.equals(storage.getType())
            ? URLUtil.normalize(storage.getDomain() + StringConstants.SLASH + fileInfo.getUrl()) : fileInfo.getUrl());
        return fileInfo;
    }

    @Override
    public Long countByStorageIds(List<Long> storageIds) {
        return baseMapper.lambdaQuery().in(FileDO::getStorageId, storageIds).count();
    }

    @Override
    protected void fill(Object baseObj) {
        if (baseObj instanceof FileResp fileResp && !URLUtils.isHttpUrl(fileResp.getUrl())) {
            StorageDetailResp storage = storageService.get(fileResp.getStorageId());
            fileResp.setUrl(URLUtil.normalize(storage.getDomain() + StringConstants.SLASH + fileResp.getUrl()));
        }
        super.fill(baseObj);
    }
}