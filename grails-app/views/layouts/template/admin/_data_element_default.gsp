<!-- data-elements-default-table -->
<script type="text/template" class="template" id="data-elements-default-table">
<table class="ehrTable table table-striped ">
	<thead><tr>
		    <th>Location</th>		
		    <!--<th>Values Type</th>-->		
			<th>Values Type<span class="required">*</span></th>    
		    <th>{{ehr_element}}</th>
			<th class="f-btn"></th>
			<th class="f-btn"></th>
		    </tr>
    </thead>				
    <tbody></tbody>
</table>
</script>

<!-- data-elements-default -->
<script type="text/template" class="template" id="single-data-elements-default">
 <td><input type="text" class="location" id="location" value="{{loc}}"></td>
 <!--<td><select class="slcValueType">				
				<option value="NotApplicable">NotApplicable</option>				  
                <option value="IMO_Code">IMO_Code</option>                
				<option value="Query_Mnemonic">Query/Mnemonic</option>
				<option value="HospitalSpecific">HospitalSpecific</option>
				<option value="StandardCode">StandardCode</option>
				<option value="ValueSet">ValueSet</option>
     </select>
 </td>-->
 <td><select class="slcValuesType" multiple="multiple"></select></td>
 <td><select class="slcParent"></select></td>
 <td class='f-btn'><div id="plus-btn" class="btn"><i class="icon-plus"></i></div></td>
 <td class='f-btn'><div id="minus-btn" class="btn"><i class="icon-minus"></i></div></td>
</script>

<!-- default-element Option -->
<script type="text/template" class="template" id="default-element-option">
 <option value={{id}}>{{code}}</option>
</script>

<!-- multiple default-element Option -->
<script type="text/template" class="template" id="multiple-default-element-option">
 <option value={{id}}>{{code}}</option>
</script>