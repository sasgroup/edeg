<!-- LIST -->
<script type="text/template" class="template" id="product-list-template">
<div class="container" >
    <div class="row">         
			<div class="row">
				<div class="span10">
					<h3>List of Products</h3>
				</div>				
				<div class="span2">
					<a class= "add-btn btn" id="create_product"  href="javascript:;">Add New</a>
				</div>
			</div>       
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="product-list" accept-charset="utf-8">
                <table id="table_items" class="table table-striped" >
                    <thead>
                        <tr>
                            <th>
                                ID
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Description
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

<!-- View -->
<script type="text/template" class="template" id="product-template">
<div class="container">
    <div class="row">      
            <h3>{{state}}</h3>
             <form method="post" class="form-horizontal" id="product-edit" accept-charset="utf-8">
				<table class="table" id="fields">
                    <tbody>
                          <tr>
                            <td>ID<span class="required">*</span></td>
                            <td><input name="code" type="text" value="{{ code }}" id="code"></td>
                            <td rowspan="2">
								Description<br />
								<a id="btnHelp" href="javascript:;" >Help <i class="icon-question-sign"></i></a>
							</td>
                            <td rowspan="2" colspan="2">
                                <textarea rows="3" name="notes" id="notes">{{ notes }}</textarea>                  
                            </td>                            
                         </tr>      

                         <tr>
                            <td>Name<span class="required">*</span></td>
                            <td>
                                <input name="name" type="text" value="{{ name }}" id="name">
                            </td>
							               
                         </tr>                                    
                    </tbody>
                </table>   
    			
					<div id="myHelp" class="modal hide fade">
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    					<h3>Help for Product [ {{ name }} ]</h3>
    				</div>
    				<div class="modal-body">
    					<textarea rows="8" name="help" id="help" class="helpArea">{{ help }}</textarea>
    				</div>
    			</div>
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#measures">Measures</a>
                  </li>
                 <li class="">
                    <a data-toggle="tab" href="#hospitals">Hospitals</a>
                 </li>
                </ul>
         
    			<div id="myTabContent" class="tab-content">
            		<div id="measures" class="tab-pane fade active in">                
            		</div>
            		<div id="hospitals" class="tab-pane fade">              
            		</div>
        		</div>

                <div class="form-actions">					 
                    <button type="submit" class="btn btn-info pull-right">Save Product</button>
					<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
                </div>
				
            </form>
        </div>
</div>

</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-product">
 <td>{{ code }}</td>
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>


<script type="text/template" class="template" id="single-product-measure">
	<label class="checkbox">
    	<input type="checkbox" name="measure" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>


<script type="text/template" class="template" id="single-product-hospital">
	<label class="Hospitals">
         {{name}}
    </label>
</script>



