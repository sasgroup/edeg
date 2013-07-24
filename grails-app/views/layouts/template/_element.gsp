<!-- LIST -->
<script type="text/template" class="template" id="element-list-template">
<div class="container" >
    <div class="row">    
            <h3>List of DataElements</h3>
    		<section class="row-fluid">
      			<a id="create_dataElement">Create New</a>
    		</section>           
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
                			<th class="f-btn">Edit</th>
                			<th class="f-btn">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </form>
    </div>
</div>
</script>


<!-- EDIT / NEW-->
<script type="text/template" class="template" id="element-template">
<div class="container">
    <div class="row">       
            <h3>DataElement:  {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="element-edit" accept-charset="utf-8">
               <div class="container">
  				    <div class="row">
    				  <div class="span5">      				  
                        <div class="row-fluid">
                          <label for="code" class="span3">Code<span class="required">*</span></label>    
					      <input name="code" type="text" class="span9" value="{{ code }}" id="code">
					    </div>					  
					    <div class="row-fluid">	
                          <label for="name" class="span3">Name<span class="required">*</span></label>                                         
                          <input name="name" type="text" class="span9" value="{{ name }}" id="name">
					   </div> 					
					  </div>    				  
					  <div class="span7">
					    <div class="row-fluid">	
      				      <label for="notes" class="span2">Notes</label>                    
                          <textarea rows="3" class="span10" name="notes" id="notes">{{ notes }}</textarea>
    				    </div>
					  </div>
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
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info pull-right">Save DataElement</button>
					<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
                </div>
            </form>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-element">
 <td>{{ code }}</td>
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<script type="text/template" class="template" id="single-element-measure">
    <label class="checkbox">
        <input type="checkbox" name="measure" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>
