<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${data.name}</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; margin: 1rem; }
        h2 { margin-top: 1.4rem; font-size: 1.05rem; color: #333; border-bottom: 1px solid #ddd; padding-bottom: 4px; }
        .status { display: inline-block; padding: 2px 8px; border-radius: 4px; color: white; font-weight: 600; }
        .s2xx { background: #2e7d32; } .s3xx { background: #f9a825; } .s4xx { background: #ef6c00; } .s5xx { background: #c62828; }
        pre { background: #f5f7fa; border: 1px solid #e0e4ea; padding: 10px; border-radius: 4px; overflow-x: auto; font-size: 0.85rem; max-height: 800px; }
        table { border-collapse: collapse; width: 100%; font-size: 0.9rem; }
        td { padding: 4px 8px; border-bottom: 1px solid #eee; vertical-align: top; }
        td.k { color: #666; width: 30%; font-weight: 500; }
    </style>
</head>
<body>
    <h2>Response</h2>
    <#assign code = (data.responseCode)!0>
    <#assign cls = "s2xx">
    <#if code gte 500><#assign cls = "s5xx">
    <#elseif code gte 400><#assign cls = "s4xx">
    <#elseif code gte 300><#assign cls = "s3xx">
    </#if>
    <p><span class="status ${cls}">HTTP ${code}</span></p>

    <#if data.headers?? && data.headers?size != 0>
    <h2>Headers</h2>
    <table>
        <#list data.headers as name, value>
        <tr><td class="k">${name}</td><td>${value}</td></tr>
        </#list>
    </table>
    </#if>

    <#if data.body??>
    <h2>Body</h2>
    <pre>${data.body}</pre>
    </#if>
</body>
</html>
