<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="IssueTracker">
<ul>
  <#list  issue as item>
      <li>
          <a href = "/issue/${item.id}">${item.name}</a>
          <#if item.status.name()=="Created">Created</#if>
          <#if item.status.name()=="Resolved">Resolved</#if>
          <#if item.status.name()=="Closed">Closed</#if>

          <small>&mdash; ${item.publicationDate}</small>
      </li>
  </#list>
</ul>

</@layout.masterTemplate>