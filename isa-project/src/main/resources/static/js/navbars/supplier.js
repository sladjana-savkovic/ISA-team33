$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav">'
		+ ' <li class="nav-item">'
        + '  <a class="nav-link" href="/html/supplier/orders.html">Orders received</a>'
        + '  </li>'
		+ '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/supplier/offer_search.html">Offer search</a>'
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