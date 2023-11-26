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

package top.charles7c.cnadmin.system.model.req;

import java.io.Serial;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import top.charles7c.cnadmin.common.constant.RegexConstants;
import top.charles7c.cnadmin.common.enums.DisEnableStatusEnum;
import top.charles7c.cnadmin.common.enums.GenderEnum;
import top.charles7c.continew.starter.extension.crud.base.BaseReq;

/**
 * 创建或修改用户信息
 *
 * @author Charles7c
 * @since 2023/2/20 21:03
 */
@Data
@Schema(description = "创建或修改用户信息")
public class UserReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegexConstants.USERNAME, message = "用户名长度为 4 到 64 位，可以包含字母、数字，下划线，以字母开头")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "昵称长度为 2 到 30 位，可以包含中文、字母、数字、下划线，短横线")
    private String nickname;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "123456789@qq.com")
    @Pattern(regexp = "^$|" + RegexConstants.EMAIL, message = "邮箱格式错误")
    @Length(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码", example = "13811111111")
    @Pattern(regexp = "^$|" + RegexConstants.MOBILE, message = "手机号码格式错误")
    private String phone;

    /**
     * 性别
     */
    @Schema(description = "性别（0：未知；1：男；2：女）", type = "Integer", allowableValues = {"0", "1", "2"}, example = "1")
    @NotNull(message = "性别非法")
    private GenderEnum gender;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "5")
    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    /**
     * 所属角色
     */
    @Schema(description = "所属角色", example = "2")
    @NotEmpty(message = "所属角色不能为空")
    private List<Long> roleIds;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 状态
     */
    @Schema(description = "状态（1：启用；2：禁用）", type = "Integer", allowableValues = {"1", "2"}, example = "1")
    private DisEnableStatusEnum status;
}
