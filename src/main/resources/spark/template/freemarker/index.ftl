<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="IssueTracker">
<ul>
  <#list  issue as item>
      <li>
          <a href = "/issue/${item.id}">${item.name}</a>
          <#if item.status=0>Created</#if>
          <#if item.status=1>Resolved</#if>
          <#if item.status=2>Closed</#if>
          <small>&mdash; ${item.publicationDate}</small>
      </li>
  </#list>
</ul>

</@layout.masterTemplate>