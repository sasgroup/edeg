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
					<label for="slcReportType" class="span2">Report Type</label>
					<select id="slcReportType" class="span5">
						<option value="1">Measure Gap Report</option>
						<option value="2">Measure Completeness Report</option>
						<option value="3">Hospital ~ Products</option>
						<option value="4">Hospital ~ Measures</option>
						<option value="5">Hospital ~ Data Elements</option>
					</select>
				</div>
				<div class="row-fluid">
					<label for="slcHospital" class="span2">Hospital</label>
					<select id="slcHospital" class="span5">
						<option value="0"> - All Hospitals - </option>
					</select>
					<button type="button" id="btnGenerateReports">Generate Reports</button>
				</div>
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


