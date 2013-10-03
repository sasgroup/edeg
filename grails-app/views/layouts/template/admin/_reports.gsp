<!-- View -->
<script type="text/template" class="template" id="reports-template">
<div class="container">
    <div class="row">      
		<!--
		<h3>Reports:</h3>
		<hr />
		<div class="clearfix"></div>
		-->
		<form method="post" class="form-horizontal" id="reports-view" accept-charset="utf-8">
			<div class="container">

				<div class="row-fluid">
					<div class="span5">
						<label for="slcReportType" >Report Type</label>
						<select id="slcReportType" class="input-block-level">
							<option value="1">Measure Gap Report</option>
							<option value="2">Measure Completeness Report</option>
							<option value="3">Hospital ~ Products</option>
							<option value="4">Hospital ~ Measures</option>
							<option value="5">Hospital ~ Data Elements</option>
							<option value="6">Hospital ~ Filtered Data Elements</option>
							<option value="7">Audit Log</option>
						</select>

						<label for="slcHospital" 							hideFor="7">Hospital</label>
						<select id="slcHospital" class="input-block-level"	hideFor="7">
							<option value="0"> - All Hospitals - </option>
						</select>

					</div>
					<div class="span4">
						<label for="slcProduct" 								hideFor="7">Product</label>
						<select id="slcProduct" class="input-block-level" 		hideFor="7">
							<option value="0"> - All Products - </option>
						</select>

						<label for="slcEntityType" 								hideFor="1,2,3,4,5,6">Entity Type</label>
						<select id="slcEntityType" class="input-block-level"	hideFor="1,2,3,4,5,6">
							<option value="-"> - All Entity Types - </option>
							<option value="P">Products</option>
							<option value="M">Measures</option>
							<option value="E">Data Elements</option>
							<option value="H">Hospitals</option>
							<option value="HP">Hospital Products</option>
							<option value="HM">Hospital Measures</option>
							<option value="HE">Hospital Data Elements</option>
						</select>

						<label for="slcMeasure"								hideFor="7">Measure</label>
						<select id="slcMeasure" class="input-block-level"	hideFor="7">
							<option value="0"> - All Measures - </option>
						</select>


						<label for="dtpDateRange" 							hideFor="1,2,3,4,5,6">Date Range</label>
						<div id="dtpDateRange"								hideFor="1,2,3,4,5,6">
							<input type="text" id="dpFrom" data-date-format="mm/dd/yy" value="" class="span4"> ~ 
							<input type="text" id="dpTill" data-date-format="mm/dd/yy" value="" class="span4">
						</div>

					</div>
					<div class="span3">
						<label for="slcElement"								hideFor="7">Data Element</label>
						<select id="slcElement" class="input-block-level"	hideFor="7">
							<option value="0"> - All Elements - </option>
						</select>

						<label for="slcEntity"								hideFor="1,2,3,4,5,6">Entity</label>
						<select id="slcEntity" class="input-block-level"	hideFor="1,2,3,4,5,6">
							<option value="-"> - All Entities - </option>
						</select>


						<label for="slcElement">&nbsp;</label>
						<div class="pull-right">						
							<button type="button" class="btn btn-mini" id="btnGenerateReports">Generate Reports</button>
							<button type="button" class="btn btn-mini" id="btnGenerateExcel">Export to Excel</button>
						</div>
					</div>
				</div>
				
				<hr />

				<div class="ihm_hidden" id="divRepData"></div>
				<div class="row-fluid">
					<div id="reportResults">
						<table id="tblReportResults"></table>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

</script>

<script type="text/template" class="template" id="reports-hospitals-template">
	<option value="{{id}}">{{name}}</option>
</script>
<script type="text/template" class="template" id="reports-pme-template">
	<option value="{{id}}" title="{{name}}">{{code}}</option>
</script>


