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

package top.continew.admin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.continew.admin.system.mapper.FileMapper;
import top.continew.admin.system.model.entity.FileDO;
import top.continew.admin.system.model.query.FileQuery;
import top.continew.admin.system.model.req.FileReq;
import top.continew.admin.system.model.resp.FileResp;
import top.continew.admin.system.model.resp.FileStatisticsResp;
import top.continew.admin.system.model.resp.FileUploadResp;
import top.continew.admin.system.service.FileService;
import top.continew.starter.core.exception.BusinessException;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.starter.storage.manger.StorageManager;
import top.continew.starter.storage.model.resp.UploadResp;
import top.continew.starter.storage.strategy.StorageStrategy;

import java.io.IOException;
import java.util.List;

/**
 * 文件业务实现
 *
 * @author Charles7c
 * @since 2023/12/23 10:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends BaseServiceImpl<FileMapper, FileDO, FileResp, FileResp, FileQuery, FileReq> implements FileService {

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<FileDO> fileList = baseMapper.lambdaQuery().in(FileDO::getId, ids).list();
        fileList.forEach(file -> {
            StorageStrategy<?> instance = StorageManager.instance(file.getStorageCode());
            instance.delete(file.getBucketName(), file.getPath());
        });
    }

    @Override
    public FileUploadResp upload(MultipartFile file, String storageCode) {
        StorageStrategy<?> instance;
        if (StrUtil.isBlank(storageCode)) {
            instance = StorageManager.instance();
        } else {
            instance = StorageManager.instance(storageCode);
        }
        UploadResp uploadResp;
        try {
            uploadResp = instance.upload(file.getOriginalFilename(), null, file.getInputStream(), file
                .getContentType(), true);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败", e);
        }
        return FileUploadResp.builder().url(uploadResp.getUrl()).build();
    }

    @Override
    public Long countByStorageIds(List<Long> storageIds) {
        if (CollUtil.isEmpty(storageIds)) {
            return 0L;
        }
        return baseMapper.lambdaQuery().in(FileDO::getStorageCode, storageIds).count();
    }

    @Override
    public FileStatisticsResp statistics() {
        FileStatisticsResp resp = new FileStatisticsResp();
        List<FileStatisticsResp> statisticsList = baseMapper.statistics();
        if (CollUtil.isEmpty(statisticsList)) {
            return resp;
        }
        resp.setData(statisticsList);
        resp.setSize(statisticsList.stream().mapToLong(FileStatisticsResp::getSize).sum());
        resp.setNumber(statisticsList.stream().mapToLong(FileStatisticsResp::getNumber).sum());
        return resp;
    }
}