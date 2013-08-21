<!-- hospital_data_element-form -->
<script type="text/template" class="template" id="hospital_data_element-form">
<form id="data-element-page">    
    <h3></h3>
 	<div id="main_table">
	 <table class="table table-condensed" id="hospital-elements">
	  <thead><tr>
 				<th>DataElement</th>    
		    	<th>Location</th>           
				<th title="SourceEHR">EHR</th>
				<th>Source</th>
				<th>CodeType</th>
		    	<th>ValueType</th>
				<th class='resetAll'><a id="resetAll" title="Reset all data elements in the measure to default locations in accordance with the current EHR version"><i class="icon-retweet"></i></a> </th>
				<th>Help</th>	    		    
			 </tr>
      </thead>				
    <tbody></tbody>
    </table>
	</div>  	
		<div id="deatails">	
		<ul id="detailsTab" class="nav nav-tabs">
          <li class="active">
            <a data-toggle="tab" href="#qa2">Q&A Level 2</a>
          </li>
          <li class="qa3">
            <a data-toggle="tab" href="#qa3">Q&A Level 3</a>
          </li>
		  <li class="">
            <a data-toggle="tab" href="#hospital-specific-tab">Hospital Specific Settings</a>
          </li>	
		   <li class="">
            <a data-toggle="tab" href="#extra-location">Extra Locations</a>
          </li>
        </ul>
         
    	<div id="detailsTabContent" class="tab-content">
          <div id="qa2" class="tab-pane fade active in">
				<form id="tab-qa2">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details">Click on the Data Element to view its details</div>					
					<textarea rows="2" class="input-xlarge"></textarea> <a class="btn btn-info">Send</a>					
				</form>
          </div>
          <div id="qa3" class="tab-pane fade">
		  		<form id="tab-qa3">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"></div>					
					<textarea rows="2" class="input-xlarge"></textarea> <a class="btn btn-info">Send</a>	
				</form>
          </div>		  
		  <div id="hospital-specific-tab" class="tab-pane fade">             
		    <div class="row-fluid">						
				<div class="span7">             
				<table id="hospital-specific-table" class="table table-bordered table-condensed">				
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
			 <table id="extra-table" class="table table-condensed table-bordered">				
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
 

<div class="form-actions">					 
                    
<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
<button id="save-mark-btn" class="btn btn-info pull-right">Save&Mark</button>
<button id="save-btn" class="btn btn-info pull-right">Save</button>			
</div>
</form>
</script>

<!-- hospital_data_element -->
<script type="text/template" class="template" id="hospital_data_element">
 <td id="{{id}}" class="slc_row" title="{{element_notes}}">{{dataElement}}</td>
 <td><input type="text" class="location" name="location" id="location" value="{{location}}"></td>
 <td><input type="checkbox" class="sourceEHR" name="sourceEHR" id="sourceEHR" value={{sourceEHR}}></td>
 <td><input type="text" class="source" id="source" name= "source" value="{{source}}"></td>
 <td>
    <select class="slcCodeType" name="codeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable">NotApplicable</option>
				 <option value="AdministrativeSex">AdministrativeSex</option>                 
				 <option value="CDCREC">CDREC</option>
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
 <td>
    <select class="slcValueType" name="valueType">			
				<option value="">-Select-</option>
				<option value="NotApplicable">NotApplicable</option>     
                <option value="IMO_Code">IMO_Code</option>                
				<option value="Query_Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>
 <!--<td>
   <div id="reset" class="btn" title="Reset the data element location to default in accordance with the current EHR version">Reset</div>
 </td>-->
<td class="f-btn"><div id="reset" class= "btn" title="Reset the data element location to default in accordance with the current EHR version"><i class="icon-refresh"></i></div></td>
<td><a class="btn show_info" rel="tooltip" did="{{id}}" title=""><i class="icon-info-sign" did="{{id}}"></i></a></td>
</script>


<!--extra-elements -->
<script type="text/template" class="template" id="extra-elements">
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
				<option value="Query_Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>
 <td><div id="plus-btn" class="btn btn-mini"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn btn-mini"><i class="icon-minus"></i></div></td>
</script>

<!-- hospital-specific -->
<script type="text/template" class="template" id="hospital-specific">
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

<!-- qa -->
<script type="text/template" class="template" id="qa">
  <div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"><p>{{notes}}</p></div>					
  <textarea rows="2" class="txt-message input-xlarge"></textarea> <a class="btn btn-info send-btn">Send</a>
</script>
