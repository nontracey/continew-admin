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

package top.charles7c.cnadmin.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import top.charles7c.cnadmin.system.model.entity.UserDO;
import top.charles7c.cnadmin.system.model.query.UserQuery;
import top.charles7c.cnadmin.system.model.req.UserBasicInfoUpdateReq;
import top.charles7c.cnadmin.system.model.req.UserReq;
import top.charles7c.cnadmin.system.model.req.UserRoleUpdateReq;
import top.charles7c.cnadmin.system.model.resp.UserDetailResp;
import top.charles7c.cnadmin.system.model.resp.UserResp;
import top.charles7c.continew.starter.extension.crud.base.BaseService;

/**
 * 用户业务接口
 *
 * @author Charles7c
 * @since 2022/12/21 21:48
 */
public interface UserService extends BaseService<UserResp, UserDetailResp, UserQuery, UserReq> {

    /**
     * 保存用户信息
     * 
     * @param user
     *            用户信息
     * @return ID
     */
    Long save(UserDO user);

    /**
     * 上传头像
     *
     * @param avatar
     *            头像文件
     * @param id
     *            ID
     * @return 新头像路径
     */
    String uploadAvatar(MultipartFile avatar, Long id);

    /**
     * 修改基础信息
     *
     * @param updateReq
     *            修改信息
     * @param id
     *            ID
     */
    void updateBasicInfo(UserBasicInfoUpdateReq updateReq, Long id);

    /**
     * 修改密码
     *
     * @param oldPassword
     *            当前密码
     * @param newPassword
     *            新密码
     * @param id
     *            ID
     */
    void updatePassword(String oldPassword, String newPassword, Long id);

    /**
     * 修改手机号
     *
     * @param newPhone
     *            新手机号
     * @param currentPassword
     *            当前密码
     * @param id
     *            ID
     */
    void updatePhone(String newPhone, String currentPassword, Long id);

    /**
     * 修改邮箱
     *
     * @param newEmail
     *            新邮箱
     * @param currentPassword
     *            当前密码
     * @param id
     *            ID
     */
    void updateEmail(String newEmail, String currentPassword, Long id);

    /**
     * 重置密码
     *
     * @param id
     *            ID
     */
    void resetPassword(Long id);

    /**
     * 修改角色
     *
     * @param updateReq
     *            修改信息
     * @param id
     *            ID
     */
    void updateRole(UserRoleUpdateReq updateReq, Long id);

    /**
     * 根据用户名查询
     *
     * @param username
     *            用户名
     * @return 用户信息
     */
    UserDO getByUsername(String username);

    /**
     * 根据手机号查询
     *
     * @param phone
     *            手机号
     * @return 用户信息
     */
    UserDO getByPhone(String phone);

    /**
     * 根据邮箱查询
     *
     * @param email
     *            邮箱
     * @return 用户信息
     */
    UserDO getByEmail(String email);

    /**
     * 根据部门 ID 列表查询
     *
     * @param deptIds
     *            部门 ID 列表
     * @return 用户数量
     */
    Long countByDeptIds(List<Long> deptIds);
}
