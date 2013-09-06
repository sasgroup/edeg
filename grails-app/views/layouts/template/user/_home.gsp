<!-- user-home-page -->
<script type="text/template" class="template" id="home-page">
<div class="my-frame">
	<h2>Welcome</h2>
	<p>Welcome to IHM Services Electronic Data Element Guide (eDEG).</p>
	<p>This tool will allow you to specify where in your hospital's data the information IHM needs is located. The toll helps to facilitate the
	communication between all parties so that IHM can ensure they are accesing the correct data and using it in the correct place 
	within our products.</p>

	<p>For more information on how to use the eDEG refer to our User Guide. <img class="icon_pdf" src="/ihm/static/css/images/icon_pdf.gif"><a href="/ihm/static/uploadFiles/eDEGGuide.pdf">IHM Services eDEG Guide.pdf</a></p>

	<p>To get started select a product from the tabs above.</p>
</div>

<div class="my-frame">
	<h2>Hospital Information</h2>

    <table>
		<tbody>
			<tr>
				<td>Hospital Name: {{hospital}} </td>
				<td class="column2">IHM Products: {{products}}</td>				
			</tr>
			<tr>
				<td>Primary EHR: {{pr_ehr}} </td>
				<td class="column2">Meaningful Use Population Methode: {{pop_methode}}</td>
			</tr>
			<tr>
				<td>Additional EHRs: {{add_ehrs}}</td>
				<td></td>
			</tr>
		<tbody>
	</table>	
</div>
</script>


