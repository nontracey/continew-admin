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

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.continew.admin.common.context.UserContextHolder;
import top.continew.admin.system.enums.FileTypeEnum;
import top.continew.admin.system.mapper.FileMapper;
import top.continew.admin.system.model.entity.FileDO;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.storage.dao.StorageDao;
import top.continew.starter.storage.model.resp.UploadResp;

/**
 * 存储持久层接口本地实现类
 *
 * @author Charles7c
 * @author echo
 * @since 2023/12/24 22:31
 */
@Slf4j
@RequiredArgsConstructor
public class StorageDaoImpl implements StorageDao {

    private final FileMapper fileMapper;

    @Override
    public void add(UploadResp uploadResp) {
        FileDO file = new FileDO();
        file.setStorageCode(uploadResp.getCode());
        String originalFilename = EscapeUtil.unescape(uploadResp.getOriginalFilename());
        file.setName(StrUtil.contains(originalFilename, StringConstants.DOT)
            ? StrUtil.subBefore(originalFilename, StringConstants.DOT, true)
            : originalFilename);
        file.setUrl(uploadResp.getUrl());
        file.setPath(uploadResp.getBasePath());
        file.setSize(uploadResp.getSize());
        file.setThumbnailUrl(uploadResp.getThumbnailUrl());
        file.setThumbnailSize(uploadResp.getThumbnailSize());
        file.setExtension(uploadResp.getExt());
        file.setType(FileTypeEnum.getByExtension(file.getExtension()));
        file.setETag(uploadResp.geteTag());
        file.setBucketName(uploadResp.getBucketName());
        file.setCreateTime(uploadResp.getCreateTime());
        file.setUpdateUser(UserContextHolder.getUserId());
        file.setUpdateTime(file.getCreateTime());
        fileMapper.insert(file);
    }
}