$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav">'
		+ ' <li class="nav-item">'
        + '  <a class="nav-link" href="">Orders</a>'
        + '  </li>'
		+ '  <li class="nav-item">'
        + '  <a class="nav-link" href="">Offer</a>'
        + '  </li>'
		+ ' <li class="nav-item">'
        + '  <a class="nav-link" href="/html/supplier/supplier_profile.html">Profile</a>'
        + '  </li>'
        + ' <li class="nav-item">'
        + '  <a class="nav-link" href="">Logout</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});