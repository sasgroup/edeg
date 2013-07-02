<!-- LIST -->
<script type="text/template" class="template" id="measure-list-template">
<div class="container" >
    <div class="row">
        <div class="span8">
            <h3>List of Measures</h3>
    		<section class="row-fluid">
      			<a id="create_measure">Create New</a>
    		</section>
            <hr>
            <div class="clearfix"></div>
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


<!-- EDIT/NEW -->
<script type="text/template" class="template" id="measure-template">
<div class="container">
    <div class="row">
        <div class="span8">
            <h3>Measure: {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="measure-edit" accept-charset="utf-8">
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

				<div class="control-group">
					<label for="category" class="control-label">
						Category
					</label>
					<div class="controls">
					  <select id="measureCategory">
						<option></option>								
					  </select>
					</div>
				</div>

				<div class="control-group">
					<label for="cqmDomain" class="control-label">
						CQMDomain
					</label>
					<div class="controls">
					  <select id="cqmDomain">	
						<option></option>								
					  </select>
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
                    <button type="submit" class="btn btn-info">Save Measure</button>
                </div>
            </form>
        </div>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-measure">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td> 
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
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