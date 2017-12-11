
-- 创建数据库
CREATE DATABASE IF NOT EXISTS `xf`
  DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- 视频表
CREATE TABLE  `t_media` (
  `id` VARCHAR(32) NOT NULL COMMENT '主键',
  `file_name` VARCHAR(200) COMMENT  '文件名称',
  `media_path` VARCHAR(200)  COMMENT '文件路径',
  `media_size`  BIGINT COMMENT '文件大小,单位字节',
  `media_status` INT COMMENT '文件状态,0:新上传,1:审核通过,2:审核不通过,3:已使用',
  `file_md5` VARCHAR(100) COMMENT '文件md5',
  `uploader_id` VARCHAR(32) COMMENT '上传人id',
  `auditor_id` VARCHAR(32) COMMENT '审核人id',
  `audit_time` VARCHAR(32) COMMENT '审核时间',
  `creator_id` VARCHAR(32) COMMENT '创建人id',
  `create_time` TIMESTAMP COMMENT '创建时间',
  `editor_id` VARCHAR(32) COMMENT '修改人id',
  `edit_time` TIMESTAMP  NULL ON UPDATE  current_timestamp  COMMENT '编辑时间',
  PRIMARY KEY (`id`)
) COMMENT '视频表';


-- 用户表
CREATE TABLE  `t_user` (
  `id` VARCHAR(32) NOT NULL COMMENT '主键',
  `user_name` VARCHAR(100) COMMENT '用户名称',
  `user_sex` INT(1) COMMENT '性别,0:男,1:女',
  `user_phone` VARCHAR(15) COMMENT '手机号码',
  `user_status` INT(1) COMMENT '用户状态,0:新建,1:正常,2:锁定',
  `creator_id` VARCHAR(32) COMMENT '创建人id',
  `create_time` TIMESTAMP COMMENT '创建时间',
  `editor_id` VARCHAR(32) COMMENT '修改人id',
  `edit_time` TIMESTAMP  NULL ON UPDATE  current_timestamp COMMENT '编辑时间',
  PRIMARY KEY (`id`)
) COMMENT '用户表';


-- 课程表
CREATE TABLE  `t_course` (
  `id` VARCHAR(32) NOT NULL COMMENT '主键',
  `course_name` VARCHAR(200) COMMENT '课程名称',
  `course_status` INT(1) COMMENT '课程状态,0:新建,1:提交(待审批),2:审批不通过,3:审批通过,4:发布',
  `course_free` INT(1) COMMENT '课程是否收付费,0:免费,1:收费',
  `cover_path` VARCHAR(200) COMMENT '封面图片路径',
  PRIMARY KEY (`id`)
)COMMENT '课程表';
