<!-- LIST -->
<script type="text/template" class="template" id="measure-list-template">
<div class="container" >
    <div class="row">           
			<div class="row">
				<div class="span10">
					<h3>List of Measures</h3>
				</div>				
				<div class="span2">
					<a class= "add-btn btn" id="create_measure"  href="javascript:;">Add New</a>
				</div>
			</div>       		
            <form method="post" class="form-horizontal" id="measure-list" accept-charset="utf-8">
                <table id="table_items" class="table table-striped" >
                    <thead>
                        <tr>
                            <th>
								Category
                            </th> 
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


<!-- EDIT/NEW -->
<script type="text/template" class="template" id="measure-template">
<div class="container">
    <div class="row">       
            <h3>{{state}}</h3>            
            <form method="post" class="form-horizontal" id="measure-edit" accept-charset="utf-8">				    
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

                         <tr>
                            <td>Category</td>
                            <td><select id="measureCategory"></td>
                            <td rowspan="2">
                              CQM Domain                              
                            </td>
                            <td>
                                <select id="cqmDomain" cqmDomain">                  
                            </td>                            
                         </tr>
                    </tbody>
           			</table>     
			
				<div id="myHelp" class="modal hide fade">
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    					<h3>Help for Measure [ {{ name }} ]</h3>
    				</div>
    				<div class="modal-body">
    					<textarea rows="8" name="help" id="help" class="helpArea">{{ help }}</textarea>
    				</div>
    			</div>				
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#products">Products</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#elements">Data Elements</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="products" class="tab-pane fade active in">    
                   </div>
                   <div id="elements" class="tab-pane fade">              
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info pull-right">Save Measure</button>
					<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
                </div>
            </form>       
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-measure">
 <td>{{ category }}</td>
 <td>{{ code }}</td>
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
</script>

<script type="text/template" class="template" id="single-measure-product">
    <label class="checkbox">
        <input type="checkbox" name="product" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>


<script type="text/template" class="template" id="single-measure-element">
    <label class="checkbox">
        <input type="checkbox" name="element" value="{{name}}" id="{{id}}" {{ch}}> {{name}}
    </label>
</script>

<script type="text/template" class="template" id="single-measure-category">
    <option {{selected}} value={{id}}>{{name}}</option>
</script>

<script type="text/template" class="template" id="single-measure-domain">
    <option {{selected}} value={{id}}>{{name}}</option>
</script>