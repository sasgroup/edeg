<!-- LIST -->
<script type="text/template" class="template" id="measure-list-template">
<div class="container" >
    <div class="row">       
            <h3>List of Measures</h3>
			<a id="create_measure" class="create_new">Create New</a>    		
            <form method="post" class="form-horizontal" id="measure-list" accept-charset="utf-8">
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


<!-- EDIT/NEW -->
<script type="text/template" class="template" id="measure-template">
<div class="container">
    <div class="row">       
            <h3>Measure: {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="measure-edit" accept-charset="utf-8">               
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
						
					   <div class="row-fluid">	
                          <label for="category" class="span3">Category</label>                                         
                          <select id="measureCategory" class="span9">
							<option></option>								
					  	  </select>
					   </div> 
	 					
					  </div>    				  
					  <div class="span7">
					    <div class="row-fluid">	
      				      <label for="notes" class="span2">Notes</label>                    
                          <textarea rows="3" class="span10" name="notes" id="notes">{{ notes }}</textarea>
    				    </div>
						
						<div class="row-fluid">	
                          <label for="cqmDomain" class="span2 cqmDomain">CQMDomain</label>                                         
                          <select id="cqmDomain" class="span10 cqmDomain">
							<option></option>								
					  	  </select>
					   </div> 

					  </div>
  				    </div>
				</div>				
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#products">Products</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#elements">DataElements</a>
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
					<button id="cancel" class="btn btn-info pull-right">Cancel</button>
                </div>
            </form>       
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-measure">
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