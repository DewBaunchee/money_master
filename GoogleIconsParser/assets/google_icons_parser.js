JSON.stringify(
    Object.fromEntries(
        [...document.querySelectorAll("icons-group")].map(group =>
            [
                group.querySelector(".group-title").innerHTML.trim(),
                [...group.querySelectorAll("gf-load-icon-font")].map(icon => icon.innerHTML.trim())
            ]
        )
    )
)