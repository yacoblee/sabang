<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="sub-upload">
	<div class="">
		<select id="svyType" name="svyType" title="조사유형">
			<option value="사방댐">사방댐 외관점검</option>
			<option value="산지사방">산지사방 외관점검</option>
			<option value="계류보전">계류보전 외관점검</option>
		</select>
	</div>
	<div class="drag-div drag-active">
		<p class="drag-msg noselect">파일을 드래그하세요.</p>
	</div>
	<div class="BtnGroup">
	    <div class="BtnRightArea">
	        <button type="button" class="upload-btn" onclick="javascript:fncUploadStripLandInsert(); return false;">업로드</button>
	    </div>
	</div>
</div>