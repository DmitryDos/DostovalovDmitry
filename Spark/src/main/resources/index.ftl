<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>ArticlesDatabase</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/gh/yegor256/tacit@gh-pages/tacit-css-1.6.0.min.css"/>
</head>

<body>

<div style="display: flex; justify-content: center; align-items: center; border: 4px solid black;"><span>Список книг</span></div>
<table style="text-align: center; border-spacing: 4px; border: 4px solid black; background: rgb(220, 220, 220)">
    <tr style="background: rgb(80, 80, 80); color: white;">
        <th style="text-align: center; border: 1px solid black;">Id</th>
        <th style="text-align: center; border: 1px solid black;">Name</th>
        <th style="text-align: center; border: 1px solid black;">Number of comments</th>
    </tr>
    <#list articles as article>
        <tr onmouseover="this.querySelector('td').style.color = 'white'; this.style.background = 'rgb(140, 140, 140)';" onmouseleave="this.querySelector('td').style.color = 'black'; this.style.background = 'rgb(160, 160, 160)';" style="background: rgb(160, 160, 160); color: black;">
            <td style="border: 1px solid black;">${article.id}</td>
            <td style="border: 1px solid black;">${article.name}</td>
            <td style="border: 1px solid black;">${article.numberOfComments}</td>
        </tr>
    </#list>
</table>

</body>

</html>