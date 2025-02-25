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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.continew.admin.system.enums.OptionCategoryEnum;
import top.continew.admin.system.mapper.FileMapper;
import top.continew.admin.system.service.OptionService;
import top.continew.starter.storage.dao.StorageDao;

import java.util.Map;

/**
 * 文件存储配置加载器
 *
 * @author Charles7c
 * @author echo
 * @since 2023/12/24 22:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageConfigLoader implements ApplicationRunner {

    private final OptionService optionService;
    private final FileStorageInit fileStorageInit;

    @Override
    public void run(ApplicationArguments args) {
        // 查询存储配置
        Map<String, String> map = optionService.getByCategory(OptionCategoryEnum.STORAGE);
        // 加载存储配置
        fileStorageInit.load(map);
    }

    /**
     * 存储持久层接口本地实现类
     */
    @Bean
    public StorageDao storageDao(FileMapper fileMapper) {
        return new StorageDaoImpl(fileMapper);
    }
}
