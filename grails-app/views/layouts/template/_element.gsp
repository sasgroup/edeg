<!-- LIST -->
<script type="text/template" class="template" id="element-list-template">
<div class="container" >
    <div class="row">
        <div class="span8">
            <h3>List of DataElements</h3>
    		<section class="row-fluid">
      			<a id="create_dataElement">Create New</a>
    		</section>
            <hr>    		
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-list" accept-charset="utf-8">
                <table id="table_items" class="table table-striped" >
                    <thead>
                        <tr>
                            <th>
                                Code
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Notes
                            </th>            
                			<th>Edit</th>
                			<th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
</script>


<!-- EDIT / NEW-->
<script type="text/template" class="template" id="element-template">
<div class="container">
    <div class="row">
        <div>
            <h3>DataElement:  {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-edit" accept-charset="utf-8">
                <div class="control-group">
                    <label for="code" class="control-label">
                        Code
                    </label>
                    <div class="controls">
                        <input name="code" type="text" value="{{ code }}" id="code">
                    </div>
                </div>
                <div class="control-group">
                    <label for="name" class="control-label">
                        Name
                    </label>
                    <div class="controls">
                        <input name="name" type="text" value="{{ name }}" id="name">
                    </div>
                </div>
                <div class="control-group">
                    <label for="notes" class="control-label">
                        Notes
                    </label>
                    <div class="controls">
                        <textarea rows="3" name="notes" id="notes">{{ notes }}</textarea>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#measures">Measure</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#ehrs">DataElementsDefault</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="measures" class="tab-pane fade active in">    
                   </div>
                   <div id="ehrs" class="tab-pane fade"> 
						<table id="dataElementsTable">   				 
        				</table>             
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info">Save DataElement</button>
                </div>
            </form>
        </div>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-element">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<script type="text/template" class="template" id="single-element-measure">
    <label class="checkbox">
        <input type="checkbox" name="measure" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>


<script type="text/template" class="template" id="single-element-ehr">
    <label class="checkbox">
        <input type="checkbox" name="ehr" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>

<!-- data-elements-default-table -->
<script type="text/template" class="template" id="data-elements-default-table">
<table class="ehrTable table table-striped ">
	<thead><tr>
		    <th>Location</th>           
			<th>CodeType</th>
		    <th>ValueType</th>		    
		    <th>EHR</th>
			<th></th>
		    </tr>
    </thead>				
    <tbody></tbody>
</table>
</script>

<!-- data-elements-default -->
<script type="text/template" class="template" id="single-data-elements-default">
 <td><input type="text" name="location" value="{{loc}}"></td>
 <td><select id="slcCodeType">
                 <option value="1">{{code_type}}</option>
				 <option value="1">AdministrativeSex</option>                 
				 <option value="1">CDREC</option>
				 <option value="1">CDT</option>
				 <option value="1">CPT</option>
				 <option value="1">CVX</option>
				 <option value="1">DischargeDisposition</option>
				 <option value="1">HCPCS</option>
				 <option value="1">HSLOC</option>
				<option value="1">ICD10CM</option>
				<option value="1">ICD10PCS</option>
				<option value="1">ICD9CM</option>
				<option value="1">LOINC</option>
				<option value="1">RXNORM</option>
				<option value="1">SNOMEDCT</option>
				<option value="1">SOP</option>
    </select>
 </td>
 <td><select id="slcValueType">
				 <option value="1">{{value_type}}</option>    
                 <option value="1">IMO_Code</option>                
				 <option value="1">Query/Mnemonic</option>
				 <option value="1">HospitalSpecific</option>
				 <option value="1">StandardCode</option>
				 <option value="1">ValueSet</option>
     </select>
 </td>
 <td><select id="slcEHR">
				 <option value="1">{{ehr}}</option>                            
     </select>
 </td>
 <td><div id="plus-btn" class="btn"><i class="icon-plus"></i></div></td>
</script>

<!-- EHR Option -->
<script type="text/template" class="template" id="ehr-option">
 <option value={{id}}>{{name}}</option>
</script>