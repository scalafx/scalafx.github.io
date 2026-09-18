Source code of the \ScalaFX home page. To see the current site, go to 
[http://scalafx.github.io/](http://scalafx.github.io/).

The site content is currently completely rewritten and the site creation transitioned from from [Jekyll] to [Laika].

[Laika], like ScalaFX, is using SBT (rather than Jekyll’s Ruby), so it should be more straightforward to maintain for Scala/SBT users.

## New Site

The new site documents are in `src/docs`

You can create a preview of the new site using SBT command:

```
laikaPreview
```

That should build the site and display a link to the site, something like:

```
[info] Initializing server...
[info] Delete API dir
[info] Preview server started at http://localhost:4242. Press return/enter to exit.
```


## Old Site
Created with [Jekyll].

Source code based on [Jekyll site code](https://github.com/jekyll/jekyll).

To build the site locally, use the command:

     bundle exec jekyll serve --safe --watch

Using `bundle exec jekyll ...` rather than simply `jekyll` is closer to the way 
GitHub will build it. It can help detect certain build issues.


[Jekyll]: http://jekyllrb.com/
[Laika]: https://typelevel.org/Laika/
