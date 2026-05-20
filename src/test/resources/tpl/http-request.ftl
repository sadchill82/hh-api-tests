<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${data.name}</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; margin: 1rem; }
        h2 { margin-top: 1.4rem; font-size: 1.05rem; color: #333; border-bottom: 1px solid #ddd; padding-bottom: 4px; }
        .method { display: inline-block; padding: 2px 8px; border-radius: 4px; background: #1976d2; color: white; font-weight: 600; }
        .url { word-break: break-all; font-family: ui-monospace, SFMono-Regular, Menlo, monospace; }
        pre { background: #f5f7fa; border: 1px solid #e0e4ea; padding: 10px; border-radius: 4px; overflow-x: auto; font-size: 0.85rem; }
        table { border-collapse: collapse; width: 100%; font-size: 0.9rem; }
        td { padding: 4px 8px; border-bottom: 1px solid #eee; vertical-align: top; }
        td.k { color: #666; width: 30%; font-weight: 500; }
    </style>
</head>
<body>
    <h2>Request</h2>
    <p><span class="method">${(data.method)!"GET"}</span> <span class="url">${(data.url)!"-"}</span></p>

    <#if data.headers?? && data.headers?size != 0>
    <h2>Headers</h2>
    <table>
        <#list data.headers as name, value>
        <tr><td class="k">${name}</td><td>${value}</td></tr>
        </#list>
    </table>
    </#if>

    <#if data.cookies?? && data.cookies?size != 0>
    <h2>Cookies</h2>
    <table>
        <#list data.cookies as name, value>
        <tr><td class="k">${name}</td><td>${value}</td></tr>
        </#list>
    </table>
    </#if>

    <#if data.body??>
    <h2>Body</h2>
    <pre>${data.body}</pre>
    </#if>

    <#if data.curl??>
    <h2>cURL</h2>
    <pre>${data.curl}</pre>
    </#if>
</body>
</html>
