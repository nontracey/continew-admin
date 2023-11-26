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

package top.charles7c.cnadmin.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import top.charles7c.cnadmin.common.constant.UIConstants;
import top.charles7c.continew.starter.extension.crud.base.IBaseEnum;

/**
 * 消息类型枚举
 *
 * @author Charles7c
 * @since 2023/11/2 20:08
 */
@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum implements IBaseEnum<Integer> {

    /** 系统消息 */
    SYSTEM(1, "系统消息", UIConstants.COLOR_PRIMARY),;

    private final Integer value;
    private final String description;
    private final String color;
}
