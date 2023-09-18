import gulp from "gulp";
import { deleteAsync as del } from "del";
import browserify from "browserify";
import source from "vinyl-source-stream";
import tsify from "tsify";
import uglify from "gulp-uglify";
import rename from "gulp-rename";

const { series, parallel, src, dest, task } = gulp;

function cleanDist() {
    return del(["./dist"]);
}

function copyHTML() {
    return src("./public/**/*")
        .pipe(dest("dist"));
}

function generateJS() {
    return browserify({
        basedir: ".",
        entries: ["./src/main.ts"]
    })
        .plugin(tsify)
        .bundle()
        .pipe(source("app.js"))
        .pipe(dest("dist"));
}

function generateProductionJS() {
    return src("dist/app.js")
        .pipe(rename("app.min.js"))
        .pipe(uglify())
        .pipe(dest("dist"));
}

export default series(
    cleanDist,
    parallel(copyHTML, generateJS),
    generateProductionJS
);
