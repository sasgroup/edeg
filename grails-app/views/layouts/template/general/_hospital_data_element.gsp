<!-- hospital_data_element-form -->
<script type="text/template" class="template" id="hospital_data_element-form">
<div class="container" >
<div id="data-element-page" class="row">  
    <h3></h3>
 	<div id="main_table">
	 <table class="table table-condensed" id="hospital-elements">
	  <thead><tr>
 				<th class="data-element-column">Data Element</th>    
		    	<th>Location</th>           
				<th>Source EHR</th>				
				<th>Values Type*</th>
				<th class="r-btn">Reset</th>
				<th>Help</th>	    		    
			 </tr>
      </thead>				
    <tbody></tbody>
    </table>
	</div>  	
		<div id="deatails">	
		<ul id="detailsTab" class="nav nav-tabs">
          <li class="active">
            <a data-toggle="tab" href="#qa2">Questions and Comments</a>
          </li>
          <li class="qa3">
            <a data-toggle="tab" href="#qa3">Internal IHM Comments</a>
          </li>
		  <li class="">
            <a data-toggle="tab" href="#hospital-specific-tab">Hospital Specific Value List</a>
          </li>	
		   <li class="">
            <a data-toggle="tab" href="#extra-location">Additional Locations</a>
          </li>
        </ul>
         
    	<div id="detailsTabContent" class="tab-content">
          <div id="qa2" class="tab-pane fade active in">
				<div id="tab-qa2">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details">Click on the Data Element to view its details</div>					
					<textarea rows="1" class="input-xlarge" maxlength="3900"></textarea><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a>		
				</div>
          </div>
          <div id="qa3" class="tab-pane fade">
		  		<div id="tab-qa3">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"></div>					
					<textarea rows="1" class="input-xlarge" maxlength="3900"></textarea><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a>
				</div>
          </div>		  
		  <div id="hospital-specific-tab" class="tab-pane fade">             
		    <div class="row-fluid" id="hs-table">				
			</div>					
		  </div>
		  <div id="extra-location" class="tab-pane fade"></div>
       </div>
	</div>
 

<div class="checkbox pull-right">     
    <label>
      <input type="checkbox" id="markAsComplete" {{measure_completed? 'disabled="disabled" checked ': ''}}> Mark Measure Complete
    </label>
</div>

<div class="form-actions">                  
{{role=='admin'? '<button class="btn pull-left admin-edit-notes"><i class="icon-edit"></i>Client Measure Notes</button>': ''}} 
<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
<button id="save-btn" class="btn btn-info pull-right">Save</button>			
</div>
</div>

</div>
</script>

<!-- hospital_data_element -->
<script type="text/template" class="template" id="hospital_data_element">
 <td id="{{id}}" class="slc_row" title="{{element_notes}}">{{dataElement}}</td>
 <td><div class='code'><input type="text" class="location" name="location" id="location" value="{{location}}" maxlength="1000"></div></td>
 <td>
	<select id="source" name="source" class="source">
	</select>
</td>
<td><select class="slcValuesType"></select></td>
<td class="r-btn"><div id="reset" class= "btn" title="Reset the data element location to default in accordance with the current EHR version"><i class="icon-refresh"></i></div></td>
<td><a class="btn show_info" rel="tooltip" did="{{id}}" title=""><i class="icon-info-sign" did="{{id}}"></i></a></td>
</script>


<!--extra-elements/additional location -->
<script type="text/template" class="template" id="extra-elements">
 <td><div class='code'><input type="text" class="location" id="location" value="{{location}}" placeholder="Enter Location" maxlength="1000"></div></td>
 <td>
	<select id="source" name="source" class="source">
	</select>
 </td>
 <td><select class="slcValuesType"></select></td>
 <td class='f-btn'><div id="plus-btn" class="btn"><i class="icon-plus"></i></div></td>
 <td class='f-btn'><div id="minus-btn" class="btn"><i class="icon-minus"></i></div></td>
</script>

<!-- hospital-specific -->
<script type="text/template" class="template" id="hospital-specific">
 <td><input type="text" class="code" id="code" value="{{code}}" placeholder="Enter Code" maxlength="255"></td> 
 <td><input type="text" class="mnemonic" id="mnemonic" value="{{mnemonic}}" placeholder="Enter Description" maxlength="255"></td>
 <td class='f-btn'><div id="plus-btn" class="btn"><i class="icon-plus"></i></div></td>
 <td class='f-btn'><div id="minus-btn" class="btn"><i class="icon-minus"></i></div></td>
</script>

<!-- qa -->
<script type="text/template" class="template" id="qa">
  <div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"><p>{{notes}}</p></div>					
  <textarea rows="1" class="txt-message input-xlarge" maxlength="3900"></textarea><td class="f-btn"><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a> 
</script>

<script type="text/template" class="template" id="hosp-spec-table">
<table id="hospital-specific-table" class="table table-condensed">				
<thead>
	<tr>
		<th>
			Code
		</th>		
		<th>
			Description
		</th>
		<th></th>
		<th></th>			
	</tr>					
</thead>
<tbody>					
</tbody>
</table>
</script>

<script type="text/template" class="template" id="file-upload-temp">
<div>
	<label for="txtValueSet" class="span4">Value Set Name</label>   
	<input id="txtValueSet" type="text" class="span8" placeholder="Enter Value Set Name" value="{{valueSet}}" maxlength="255">
</div>					  
<div>	
	<form id="uploadForm"  method="post" enctype="multipart/form-data" action="/ihm/api/file">
		<label class="span4">File Upload</label>  
		<input type="file"   id="fileToUpload" name="fileToUpload" {{status=="browse"? "": "class='hide'"}}/>								
    	<input type="button" id="upload" value="Upload" {{status=="upload"? "": "class='hide'"}}/>
		<input type="button" id="del"    value="Delete" {{status=="delete"? "": "class='hide'"}} />
		{{linkToFile}}
		<input type="hidden" id="currentHospitalElement" name="currentHospitalElement"/>
		<input type="hidden" id="currentMeasureId" name="currentMeasureId"/>
	</form>							
<div id="output2" class='hide'></div>
</div>	
</script>	

<script type="text/template" class="template" id="extra-table-temp">
<table id="extra-table" class="table table-condensed">				
				<thead>
        			<tr>
            			<th>Location</th>           
						<th>Source EHR</th>						
						<th>Values Type*</th>		
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
</table>
</script>