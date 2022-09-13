import markdownIt from "markdown-it/lib";
import hljs from "highlight.js/lib/common";
import 'highlight.js/styles/github.css';

export const markdown = markdownIt(
    "default",
    {
        breaks:true,
        html:false,
        xhtmlOut:false,
        highlight: function (str, lang) {
            if (lang && hljs.getLanguage(lang)) {
                try {
                    return '<pre class="hljs"><code>' +
                        hljs.highlight(str, { language: lang, ignoreIllegals: true }).value +
                        '</code></pre>';
                } catch (__) {}
            }

            return '<pre class="hljs"><code>' + markdown.utils.escapeHtml(str) + '</code></pre>';
        }
})