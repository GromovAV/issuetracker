<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="IssueTracker">

<form class="form-horizontal" role="form">
<h5>View and Comment</h5>
<div class="form-group">
    <label for="status" class="col-sm-2 control-label">Status:</label>
    <div class="col-sm-10 paddingTop">
            <#if issue.status.name()=="Created">Created</#if>
           <#if issue.status.name()=="Resolved">Resolved</#if>
           <#if issue.status.name()=="Closed">Closed</#if>
    </div>
</div>
<div class="form-group">
    <label for="publicationDate" class="col-sm-2 control-label">Start date:</label>
    <div class="col-sm-10 paddingTop">
        ${issue.publicationDate}
    </div>
</div>
<div class="form-group">
    <label for="author" class="col-sm-2 control-label">Author:</label>
    <div class="col-sm-10 paddingTop">
        <#if issue.author??>
            ${issue.author}
        </#if>
    </div>
</div>
<div class="form-group">
    <label for="description" class="col-sm-2 control-label">Description:</label>
    <div class="col-sm-10 preWrap viewDescript">
        <#if issue.author??>
            <#if issue.description??>
                ${issue.description}
            </#if>
        </#if>
    </div>
</div>

        <h6>Comments:</h6>
        </form>
         <#list  comments as com>
         <li>
            <small> ${com.author}</small>
            <small>&mdash; ${com.commentDate}</small><br>
             <small class = "preWrap"> ${com.text}</small><br>
             <#if com.changeStatus>
                Status changed to ${com.status}
             </#if>
         </li>
        </#list>

    <#if user??>
    <form class="form-horizontal" action="/comment/${issue.id}" role="form" method="post">
        <h5>Add Comment</h5>
        <div class="form-group">
            <label for="status" class="col-sm-2 control-label" >Status: </label>
            <div class="col-sm-10">
                <select name = "status" id = "status" class="form-control">
                    <#if issue.status == "Created">
                        <option value="0">Created</option>
                        <option value="1">Resolved</option>
                        <option value="2">Closed</option>
                    </#if>
                    <#if issue.status == "Resolved">
                        <option value="1">Resolved</option>
                        <option value="0">Created</option>
                        <option value="2">Closed</option>
                    </#if>
                    <#if issue.status == "Closed">
                        <option value="2">Closed</option>
                        <option value="0">Created</option>
                        <option value="1">Resolved</option>
                    </#if>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="author" class="col-sm-2 control-label">Author: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="author" placeholder="author" value="${author!}" />
            </div>
        </div>

        <div class="form-group">
            <label for="text" class="col-sm-2 control-label">Text: </label>
            <div class="col-sm-10">
                <textarea rows="5" type="text" class="form-control" name="text" placeholder="text"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Add</button>
            </div>
        </div>
    </form>
    </#if>
</@layout.masterTemplate>