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
						</select>

						<label for="slcHospital" >Hospital</label>
						<select id="slcHospital" class="input-block-level">
							<option value="0"> - All Hospitals - </option>
						</select>
					</div>
					<div class="span3">
						<label for="slcProduct">Product</label>
						<select id="slcProduct" class="input-block-level">
							<option value="0"> - All Products - </option>
						</select>

						<label for="slcMeasure">Measure</label>
						<select id="slcMeasure" class="input-block-level">
							<option value="0"> - All Measures - </option>
						</select>
					</div>
					<div class="span4">
						<label for="slcElement">Data Element</label>
						<select id="slcElement" class="input-block-level">
							<option value="0"> - All Elements - </option>
						</select>
						<label for="slcElement">&nbsp;</label>
						<div class="pull-right">						
							<button type="button" class="btn btn-mini" id="btnGenerateReports">Generate Reports</button>
							<button type="button" class="btn btn-mini" id="btnGenerateExcel">Generate Excel</button>
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


