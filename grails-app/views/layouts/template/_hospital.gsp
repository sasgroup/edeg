<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">    
        <h3>List of Hospitals</h3>        
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" accept-charset="utf-8">

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
	<h4 id="myModalLabel">HospitalElements</h4>
  </div>
  <div class="modal-body">
  	<table class="table">
	  <thead><tr>
 				<th>DataElement</th>    
		    	<th>Location</th>           
				<th>SourceEHR</th>
				<th>Source</th>
				<th>CodeType</th>
		    	<th>ValueType</th>		    
			 </tr>
      </thead>				
    <tbody></tbody>
	</table>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
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
    <hr>
       
    <ul id="myTab" class="nav nav-tabs">               
    </ul>     
    <div id="myTabContent" class="tab-content">               
    </div>

	<div class="form-actions">
                    <button id="submit-btn" class="btn btn-info pull-right">Save Hospital</button>
    </div>
	
	 </form>  
  </div>
</div>
</script>

<!-- Product/EHR Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}} name={{id}}>{{code}} </option>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td> 
</script>

<!-- hospital-measure_table -->
<script type="text/template" class="template" id="hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		    <th>Use</th>
			<th>ID</th>           
			<th>CODE</th>
		    <th>TITLE</th>		    				  
		    <th>Completed</th>
		    <th>Confirmed</th>
		    <th>Accepted</th>
		    <th>Verified</th>		    
			<th>Save</th>
 			<th>Delete</th>	
		   </tr>
    </thead>				
    <tbody></tbody>
</table>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td>
 <td>{{ id }}</td>
 <td>{{ code }}</td>
 <td><a id="customLink" href="#modalDataElements" role="button" data-toggle="modal">{{ name }}</a></td> 

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
 <td><div id="save"    class= "btn save-btn"><i class="icon-ok-circle"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>


<!-- modal-data-elements -->
<script type="text/template" class="template" id="modal-data-elements">
 <td>{{dataElement}}</td>
 <td><input type="text" class="location" id="location" value="{{location}}"></td>
 <td><input type="checkbox" class="sourceEhr" id="sourceEhr" value="{{sourceEHR}}" {{chd}}></td>
 <td><input type="text" class="source" id="source" value="{{source}}"></td>
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
</script>
