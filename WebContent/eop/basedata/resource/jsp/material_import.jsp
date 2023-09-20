<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%--<link href="${ LUI_ContextPath }/eop/basedata/resource/css/elec_import.css"
	rel="stylesheet" type="text/css" />--%>
<script type="text/javascript"
	src="${LUI_ContextPath }/eop/basedata/resource/js/vue.min.js"></script>
<script type="text/javascript"
	src="${LUI_ContextPath }/eop/basedata/resource/js/element.js"></script>
<script type="text/javascript"
		src="${LUI_ContextPath }/eop/basedata/resource/js/axios.js"></script>
<link href="${ LUI_ContextPath }/eop/basedata/resource/css/element.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript">
	LUI.ready(function() {
		seajs.use([ 'eop/basedata/resource/js/elecimport' ], function(elecimport) {
			elecimport.init({});
			elecimporter = elecimport;
		})
	})
</script>
<form name="templateForm"
	action="${ LUI_ContextPath }/eop/basedata/eop_basedata_material/MaterialImportTemplate.xls"
	method="post">
</form>
<div id="importer" style="display: none;">
	<el-dialog :visible.sync="dialogVisible" :title="dialogTitle" :close-on-click-modal="false">
		<div class="importer_container">
			<el-form label-width="80px" label-position="left">
				<el-form-item :label="firstTxt">
					<el-button type="primary" size="small" @click="downloadTemplate" class="wbtn">{{downloadTxt}}</el-button>
				</el-form-item>
				<el-form-item :label="secondTxt">
					<el-button type="primary" size="small" @click="chooseDataFile" class="wbtn">{{chooseFileTxt}}</el-button>
					<template v-if="choosedFile.fileName!=''" class="choosed_file">
						<label class="hint">{{choosedFile.fileName}}</label>
						<el-button size="small" type="text" @click.native.prevent="delChoosedFile">{{delTxt}}</el-button>
					</template>
				</el-form-item>
			</el-form>
			<div class="step hint">{{importHinterTxt_1}}</div>
			<div class="step hint">{{importHinterTxt_2}}</div>
			<div class="step hint">{{importHinterTxt_3}}</div>
			<div class="step hint">{{importHinterTxt_5}}</div>
		</div>
		<input style="display: none" id="fileUploader" type="file" name="" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" @change="showFile">
			<template slot="footer">
				<el-button type="primary" size="small" class="wbtn" @click="submit">{{confirmTxt}}</el-button>
				<el-button size="small" class="wbtn" @click="hide">{{cancelTxt}}</el-button>
			</template>
	</el-dialog>
	<el-dialog :visible.sync="resultVisible" :title="resultTitle" :close-on-press-escape="false" :close-on-click-modal="false" width="80%">
		<el-table :default-expand-all="true" :data="resultData" border>
			<el-table-column :border="false" type="expand">
				<template slot-scope="props">
					<el-table max-height="450" v-if="props.row.detail&&props.row.detail.length>0" :data="props.row.detail" border max-height="500">
						<el-table-column :label="nameLabel" prop="label" align="center" :resizable="false"></el-table-column>
						<el-table-column :label="actionLabel" align="center" :resizable="false">
							<template scope="scope"> {{optLabel(scope.row.opt)}} </template>
						</el-table-column>
						<el-table-column :label="errorInfo" align="center" :resizable="false" prop="error"> </el-table-column>
						<el-table-column :label="infoLabel" align="center" :resizable="false" width="80">
							<template scope="scope">
								<el-button v-if="scope.row.success" type="success" size="mini" icon="el-icon-check" circle></el-button>
								<el-button v-else size="mini" type="danger" icon="el-icon-close" circle @click=""></el-button>
							</template>
						</el-table-column>
					</el-table>
				</template>
			</el-table-column>
			<el-table-column :label="typeLabel" prop="label" align="center" :resizable="false"></el-table-column>
			<el-table-column :label="addLabel" prop="add" align="center" :resizable="false" width="50"></el-table-column>
			<el-table-column :label="updateLabel" prop="update" align="center" :resizable="false" width="50"></el-table-column>
			<el-table-column :label="ignoreLabel" prop="ignore" align="center" :resizable="false" width="50"></el-table-column>
			<el-table-column :label="errorLabel" prop="errorN" align="center" :resizable="false" width="50"></el-table-column>
			<el-table-column :label="infoLabel" align="center" :resizable="false" width="80">
				<template scope="scope">
					<el-button v-if="scope.row.success" type="success" size="mini" icon="el-icon-check" circle></el-button>
					<template v-else>
						<el-button v-if="scope.row.detail&&scope.row.detail.length>0" size="mini" type="danger" icon="el-icon-close" circle></el-button>
						<el-button v-else size="mini" type="danger" icon="el-icon-close" circle @click="showError(scope.row.error)"></el-button>
					</template>
				</template>
			</el-table-column>
		</el-table>
	</el-dialog>
</div>
