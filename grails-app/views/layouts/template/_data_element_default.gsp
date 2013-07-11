<!-- data-elements-default-table -->
<script type="text/template" class="template" id="data-elements-default-table">
<table class="ehrTable table table-striped ">
	<thead><tr>
		    <th>Location</th>           
			<th>CodeType</th>
		    <th>ValueType</th>		    
		    <th>{{ehr_element}}</th>
			<th></th>
			<th></th>
		    </tr>
    </thead>				
    <tbody></tbody>
</table>
</script>

<!-- data-elements-default -->
<script type="text/template" class="template" id="single-data-elements-default">
 <td><input type="text" name="location" value="{{loc}}"></td>
 <td><select class="slcCodeType">                
				 <option value=""></option>
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
				<option value=""></option>		     
                <option value="IMO_Code">IMO_Code</option>                
				<option value="Query/Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>
 <td><select class="slcEHR">	
				<option value=""></option>			
	</select>
 </td>
 <td><div id="plus-btn" class="btn"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn"><i class="icon-minus"></i></div></td>
</script>

<!-- default-element Option -->
<script type="text/template" class="template" id="default-element-option">
 <option value={{id}}>{{name}}</option>
</script>