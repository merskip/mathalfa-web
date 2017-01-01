$(function () {

    $("[data-ma-section-show-latex]").click(function () {
        var $section = $(this).closest(".ma-section");
        var $mathtex = $section.find("script[type='math/tex']");
        var latex = $mathtex.html();

        var $modal = $("#ma-latex-preview-modal");
        $modal.find(".ma-latex-preview-content").html(latex);
        $modal.modal();
    });
});