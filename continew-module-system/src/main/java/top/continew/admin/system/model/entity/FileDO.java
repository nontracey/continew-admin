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

package top.continew.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.continew.admin.system.enums.FileTypeEnum;
import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * 文件实体
 *
 * @author Charles7c
 * @since 2023/12/23 10:38
 */
@Data
@TableName("sys_file")
public class FileDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 大小（字节）
     */
    private Long size;

    /**
     * URL
     */
    private String url;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 类型
     */
    private FileTypeEnum type;

    /**
     * 缩略图大小（字节)
     */
    private Long thumbnailSize;

    /**
     * 缩略图URL
     */
    private String thumbnailUrl;

    /**
     * 存储code
     */
    private String storageCode;

    /**
     * 基础路径
     */
    private String path;

    /**
     * 存储桶
     */
    private String bucketName;

    /**
     * 文件标识
     */
    private String eTag;

}
