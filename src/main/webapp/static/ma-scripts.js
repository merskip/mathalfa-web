$(function () {

    $("[data-ma-section-show-latex]").click(function () {
        var $section = $(this).closest(".ma-section");
        var $mathtex = $section.find("script[type='math/tex']");
        var latex = $mathtex.html();

        var $modal = $("#ma-simple-modal");
        $modal.find(".modal-title").html("LaTeX preview");
        $modal.find(".modal-body").empty()
            .append($("<pre></pre>").html(latex));
        $modal.modal();
    });

    $("[data-ma-show-rpn]").click(function () {
        var rpn = JSON.parse($(this).attr("data-ma-show-rpn"));

        var $modal = $("#ma-simple-modal");
        $modal.find(".modal-title").html("Postfix notation");

        var $content = $modal.find(".modal-body").empty();
        rpn.forEach(function (fragment) {
            $("<kbd></kbd>")
                .html(fragment)
                .appendTo($content);
            $content.append(" ");
        });
        $modal.modal();
    });
});