<!-- user-home-page -->
<script type="text/template" class="template" id="home-page">
<div class="my-frame">
	<h2>Welcome</h2>
	<p>Welcome to the IHM Services Electronic Data Element Guide (eDEG).</p>
	<p>This tool will allow you to specify where in your hospital's data the information IHM needs is located. The tool helps to facilitate<br>
    the communication between all parties so that IHM can ensure they are accessing the correct data and using it in the correct place<br> 
	within our products.</p>

	<p>For more information on how to use the eDEG refer to our User Guide. <img class="icon_pdf" src="/ihm/static/css/images/icon_pdf.gif"><a href="/ihm/api/file?fileName=eDEGGuide.pdf">IHM Services eDEG Guide.pdf</a></p>

	<p>To get started select a product from the tabs above.</p>
</div>

<div class="my-frame">
	<h2>Hospital Information</h2>

    <table>
		<tbody>
			<tr>
				<td><strong>Hospital Name: </strong>{{hospital}} </td>
				<td class="column2"><strong>IHM Products: </strong>{{products}}</td>				
			</tr>
			<tr>
				<td><strong>Primary EHR: </strong>{{pr_ehr}} </td>
				<td class="column2"><strong>Meaningful Use Population Methode: </strong>{{pop_methode}}</td>
			</tr>
			<tr>
				<td><strong>{{add_ehrs==""? "": "Additional EHRs:"}} </strong>{{add_ehrs}}</td>
				<td></td>
			</tr>
		<tbody>
	</table>	
</div>
</script>


