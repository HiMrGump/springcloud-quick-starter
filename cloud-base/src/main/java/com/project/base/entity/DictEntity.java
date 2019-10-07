package com.project.base.entity;


import com.project.common.entity.BaseEntity;
import com.project.validate.ValidateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 *  字典表
 *
 * @ClassName: DictEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 16:52
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_dict")
public class DictEntity extends BaseEntity {
	//字典名称
	@NotNull(groups = {ValidateGroup.Insert.class},message = "字典名必填")
	private String name;
	//字典类型
	@NotNull(groups = {ValidateGroup.Insert.class},message = "字典类型必填")
	private String type;
	//字典码
	@NotNull(groups = {ValidateGroup.Insert.class},message = "字典码必填")
	private String code;
	//字典值
	@NotNull(groups = {ValidateGroup.Insert.class},message = "字典值必填")
	private String value;
	//排序
	private Integer sortIndex;
}
