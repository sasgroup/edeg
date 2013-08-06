<!-- user-data-element -->
<script type="text/template" class="template" id="user-data-element">
<form id="data-element-page">	
  	<div id="main_table">
	 <table class="table" id="hospital-elements">
	  <thead><tr>
 				<th>DataElement</th>    
		    	<th>Location</th>           
				<th title="SourceEHR">EHR</th>
				<th>Source</th>
				<th>CodeType</th>
		    	<th>ValueType</th>		    
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
 

<div class="form-actions">					 
                    
<button class="btn btn-info pull-right">Cancel</button>
<button class="btn btn-info pull-right">Save&Mark</button>
<button class="btn btn-info pull-right">Save</button>			
</div>
</form>
</script>

<script type="text/template" class="template" id="user-measure-breadcrumb">
<ul class="breadcrumb">
	<li><a href="#">Home</a><span class="divider">></span></li>
	<li><a href="#{{ product_code }}">{{ product_code }}</a><span class="divider">></span></li>
	<li class="active">{{ measure_code }}:</li>
	<li class="active">Data Elements</li>
</ul>
</script>

<!-- data-elements -->
<!-- <script type="text/template" class="template" id="user-data-elements">
 <td id="">Data Element</td>
 <td><input type="text" class="location" id="location" value=""></td>
 <td><input type="checkbox" class="sourceEhr" id="sourceEhr" value=""></td>
 <td><input type="text" class="source" id="source" value=""></td>
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
</script>  -->


<!-- modal-data-elements -->
<script type="text/template" class="template" id="user-data-elements">
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
</script>

