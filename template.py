import os
import tomllib

with open ("config.TOML","rb") as f:
    data = tomllib.load(f);

def render_feature(features):
    html = ""
    for f in features:
        html += f'<li>title: {f["name"]}, description: {f["description"]}</li>'
    return html;

template =  """
<html>
<head>
<title>{title}</title>
</head>
<body>
    <h1>project name: {title}</h1>
    <h1>version: {version}</h1>
    <h2>{admin_name}</h2>
    <p>{admin_description}</p>
    <ul>
        {admin_features}
    </ul>

    <h2>{user_name}</h2>
    <p>{user_description}</p>
    <ul>
        {user_features}
    </ul>
</body>
</html>

"""
html = template.format(
    title = data["title"],
    version = data["version"],
    admin_name = data["admin"]["name"],
    admin_description = data["admin"]["description"],
    admin_features = render_feature(data["admin"]["feature"]),
    user_name = data["user"]["name"],
    user_description = data["user"]["description"],
    user_features = render_feature(data["user"]["feature"])
)

with open("web/index.html","w") as file:
    file.write(html)






