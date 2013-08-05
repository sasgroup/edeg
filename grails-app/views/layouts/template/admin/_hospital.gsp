<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">    
        <h3>List of Hospitals</h3>        
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" id="hospital-list" accept-charset="utf-8">
			<table id="table_items" class="table table-striped" >
    			<thead>
        			<tr>
            			<th>
                			Name
            			</th>
            			<th>
                			Notes
            			</th>
            			<th class="f-btn">Edit</th>			
        			</tr>
    			</thead>
    			<tbody>    
    			</tbody>
			</table>
        </form>
</div>
</div>
</script>

<!-- NEW/EDIT -->
<script type="text/template" class="template" id="hospital-template">

<!-- Modal -->
<div id="modalDataElements" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	<h4 id="hospitalElementLabel">HospitalElements</h4>
    <button title="Reset the data element location to default in accordance with the current EHR version">Reset All</button>
  </div>
  <div class="modal-body">  	
  	<div id="main_table"></div>
  	<div id="deatails">	
		<ul id="detailsTab" class="nav nav-tabs">
          <li class="active">
            <a data-toggle="tab" href="#qa2">Q&A Level 2</a>
          </li>
          <li class="">
            <a data-toggle="tab" href="#qa3">Q&A Level 3</a>
          </li>
		  <li class="">
            <a data-toggle="tab" href="#hospital-specific">Hospital Specific Settings</a>
          </li>	
		   <li class="">
            <a data-toggle="tab" href="#extra-location">Extra Locations</a>
          </li>
        </ul>
         
    	<div id="detailsTabContent" class="tab-content">
          <div id="qa2" class="tab-pane fade active in">
				<form id="tab-qa2">
					<textarea id="txt-qa2" rows="3" class="input-xlarge">
					</textarea>					
					<textarea rows="1" class="input-xlarge">
					</textarea>	
					<div>
						<button class="btn btn-info">Send</button>
					</div>
				</form>
          </div>
          <div id="qa3" class="tab-pane fade">
		  		<form id="tab-qa3">
					<textarea id="txt-qa3" rows="3" class="input-xlarge">
					</textarea>					
					<textarea rows="1" class="input-xlarge">
					</textarea>	
					<div>
						<button class="btn btn-info">Send</button>
					</div>
				</form>
          </div>		  
		  <div id="hospital-specific" class="tab-pane fade">             
		    <div class="row-fluid">						
				<div class="span7">             
				<table id="modal-hospital-specific-table" class="table table-striped table-bordered">				
				<thead>
        			<tr>
            			<th>
                			Code
            			</th>
            			<th>
                			CodeType
            			</th>
            			<th>
							Mnemonic
						</th>
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
				</table>
				</div>
				<div class="span5">						
						<div class="row-fluid">
                          <label for="txtValueSet" class="span3">Value Set</label>   
					      <input id="txtValueSet" type="text" class="span9">
					    </div>					  
					    <div class="row-fluid">	
                          <label for="fileValueSets" class="span3">File Upload</label>                                         
                          <input id="fileValueSets" type="file" class="span9">
					   </div>					   	 				
				</div>
			</div>					
		  </div>
		  <div id="extra-location" class="tab-pane fade">
			 <table id="modal-extra-table" class="table table-striped table-bordered">				
				<thead>
        			<tr>
            			<th>Location</th>           
						<th title="SourceEHR">EHR</th>
						<th>Source</th>
						<th>CodeType</th>
		    			<th>ValueType</th>		
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
				</table>		   
          </div>
       </div>
	</div>
  </div>  
  <div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
	<button class="btn">Save&Mark</button>
    <button class="btn">Save</button>
   </div>
</div>


<div class="container">
   <div class="row">   
   <h3>Hospital: Edit</h3>
   <form method="post" class="form-horizontal" id="hospital-edit" accept-charset="utf-8">    
	<table class="table">
        <tbody>
			  <tr>
                <td>Name</td>
                <td>
                    <input name="name" type="text" value="{{ name }}" id="name" disabled>
                </td>
                <td>Notes</td>
                <td>
                <input name="notes" type="text" value="{{ notes }}" id="notes"> 
                </td>
                <td></td>
             </tr>					

			  <tr>
                <td>Primary EHR</td>
                <td>
                    <select id="slcEHRs" style="width:250px;">                      
                    </select>
                </td>
                <td>Products</td>
                <td>
                    <select id="slcProducts" style="width:370px;">                  
                    </select>
                </td>
                <td><button id="btnApplyHospitalOptions" class="btn btn-info">Apply</button></td>
            </tr>
        </tbody>
    </table>   
   
    <ul id="myTab" class="nav nav-tabs">               
    </ul>     
    <div id="myTabContent" class="tab-content">               
    </div>

	<div class="form-actions">
                    <button id="submit-btn" class="btn btn-info pull-right">Save Hospital</button>
					<button id="cancel" class="btn btn-info pull-right">Cancel</button>
    </div>
	
	 </form>  
  </div>
</div>
<div id="loading">
  <img id="loading-image" src="http://www.kuka-systems.com/res/icn/ajax_loader.gif" alt="Loading..." />
</div>

</script>

<!-- Product/EHR Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}} name={{id}}>{{code}} </option>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td>
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td> 
</script>

<!-- hospital-measure_table -->
<script type="text/template" class="template" id="hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		    <th>Use</th>			
			<th>CODE</th>
		    <th>TITLE</th>				    				  
		    <th>Completed</th>
		    <th>Confirmed</th>
		    <th>Accepted</th>
		    <th>Verified</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td> 
 <td>{{ code }}</td>
 <td><div class='title' title="{{name}}"><a id="customLink" href="#modalDataElements" role="button" data-toggle="modal">{{ name }}</a></div></td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}}>
 </td>
 <td>
     <input type="checkbox" name="confirmed" id="{{id}}" {{confirmed}}>
 </td>
 <td>
     <input type="checkbox" name="accepted" id="{{id}}" {{accepted}}>
 </td>
 <td>
     <input type="checkbox" name="verified" id="{{id}}" {{verified}}>
 </td>
</script>


<!-- modal-data-elements-table -->
<script type="text/template" class="template" id="hospital-elements_table">
<table class="table table-condensed" id="hospital-elements">
	  <thead><tr>
 				<th>DataElement</th>    
		    	<th>Location</th>           
				<th title="SourceEHR">EHR</th>
				<th>Source</th>
				<th>CodeType</th>
		    	<th>ValueType</th>	
				<th></th>	    
			 </tr>
      </thead>				
    <tbody></tbody>
</table>
</script>


<!-- modal-data-elements -->
<script type="text/template" class="template" id="modal-data-elements">
 <td id="{{id}}" class="slc_row">{{dataElement}}</td>
 <td><input type="text" class="location" id="location" value="{{location}}"></td>
 <td><input type="checkbox" class="sourceEhr" id="sourceEhr" value="{{sourceEHR}}" {{chd}}></td>
 <td><input type="text" class="source" id="source" value="{{source}}"></td>
 <td><select class="slcCodeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable">NotApplicable</option>
				 <option value="AdministrativeSex">AdministrativeSex</option>                 
				 <option value="CDREC">CDREC</option>
				 <option value="CDT">CDT</option>
				 <option value="CPT">CPT</option>
				 <option value="CVX">CVX</option>
				 <option value="DischargeDisposition">DischargeDisposition</option>
				 <option value="HCPCS">HCPCS</option>
				 <option value="HSLOC">HSLOC</option>
				 <option value="ICD10CM">ICD10CM</option>
				 <option value="ICD10PCS">ICD10PCS</option>
				 <option value="ICD9CM">ICD9CM</option>
				 <option value="LOINC">LOINC</option>
				 <option value="RXNORM">RXNORM</option>
				 <option value="SNOMEDCT">SNOMEDCT</option>
				 <option value="SOP">SOP</option>
    </select>
 </td>
 <td><select class="slcValueType">			
				<option value="">-Select-</option>
				<option value="NotApplicable">NotApplicable</option>     
                <option value="IMO_Code">IMO_Code</option>                
				<option value="Query/Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>
 <td>
 <div id="reset" class="btn" title="Reset the data element location to default in accordance with the current EHR version">Reset</div>
 </td>
</script>


<!-- modal-extra-elements -->
<script type="text/template" class="template" id="modal-extra-elements">
 <td><input type="text" class="location" id="location" value="{{location}}"></td>
 <td><input type="checkbox" class="sourceEhr" id="sourceEhr" value="{{sourceEHR}}" {{chd}}></td>
 <td><input type="text" class="source" id="source" value="{{source}}"></td>
 <td><select class="slcCodeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable">NotApplicable</option>
				 <option value="AdministrativeSex">AdministrativeSex</option>                 
				 <option value="CDREC">CDREC</option>
				 <option value="CDT">CDT</option>
				 <option value="CPT">CPT</option>
				 <option value="CVX">CVX</option>
				 <option value="DischargeDisposition">DischargeDisposition</option>
				 <option value="HCPCS">HCPCS</option>
				 <option value="HSLOC">HSLOC</option>
				 <option value="ICD10CM">ICD10CM</option>
				 <option value="ICD10PCS">ICD10PCS</option>
				 <option value="ICD9CM">ICD9CM</option>
				 <option value="LOINC">LOINC</option>
				 <option value="RXNORM">RXNORM</option>
				 <option value="SNOMEDCT">SNOMEDCT</option>
				 <option value="SOP">SOP</option>
    </select>
 </td>
 <td><select class="slcValueType">			
				<option value="">-Select-</option>
				<option value="NotApplicable">NotApplicable</option>	     
                <option value="IMO_Code">IMO_Code</option>                
				<option value="Query/Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>
 <td><div id="plus-btn" class="btn btn-mini"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn btn-mini"><i class="icon-minus"></i></div></td>
</script>

<!-- modal-hospital-specific -->
<script type="text/template" class="template" id="modal-hospital-specific">
 <td><input type="text" class="code" id="code" value="{{code}}"></td> 
 <td><select class="slcCodeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable">NotApplicable</option>
				 <option value="AdministrativeSex">AdministrativeSex</option>                 
				 <option value="CDREC">CDREC</option>
				 <option value="CDT">CDT</option>
				 <option value="CPT">CPT</option>
				 <option value="CVX">CVX</option>
				 <option value="DischargeDisposition">DischargeDisposition</option>
				 <option value="HCPCS">HCPCS</option>
				 <option value="HSLOC">HSLOC</option>
				 <option value="ICD10CM">ICD10CM</option>
				 <option value="ICD10PCS">ICD10PCS</option>
				 <option value="ICD9CM">ICD9CM</option>
				 <option value="LOINC">LOINC</option>
				 <option value="RXNORM">RXNORM</option>
				 <option value="SNOMEDCT">SNOMEDCT</option>
				 <option value="SOP">SOP</option>
    </select>
 </td>
 <td><input type="text" class="mnemonic" id="mnemonic" value="{{mnemonic}}"></td>
 <td><div id="plus-btn" class="btn btn-mini"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn btn-mini"><i class="icon-minus"></i></div></td>
</script>