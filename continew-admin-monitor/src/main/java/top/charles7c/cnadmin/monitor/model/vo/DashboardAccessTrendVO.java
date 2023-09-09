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

package top.charles7c.cnadmin.monitor.model.vo;

import java.io.Serializable;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 仪表盘-访问趋势信息
 *
 * @author Charles7c
 * @since 2023/9/9 20:20
 */
@Data
@Schema(description = "仪表盘-访问趋势信息")
public class DashboardAccessTrendVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @Schema(description = "日期", example = "2023-08-08")
    private String date;

    /**
     * 浏览量（PV）
     */
    @Schema(description = "浏览量（PV）", example = "1000")
    private Long pvCount;

    /**
     * IP 数
     */
    @Schema(description = "IP 数", example = "500")
    private Long ipCount;
}
