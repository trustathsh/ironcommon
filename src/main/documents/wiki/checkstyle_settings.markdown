### checksytle rules

the checkstyle rules can you find in ironcommon `/src/main/documents/trustAtHsHCheckstylePolicy.xml`

[trustAtHsHCheckstylePolicy.xml](https://github.com/trustathsh/ironcommon/blob/master/src/main/documents/trustAtHsHCheckstylePolicy.xml)

--------------------------------------------------------------

### exclude folders

exclude folders from checkstyle:

##### maven-checkstyle-plugin
add the following XML-code into the maven-checkstyle-plugin
```XML
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-checkstyle-plugin</artifactId>
   <version>2.10</version>
   <configuration>
      ...

      <!-- exclude folder-->
      <excludes>path of the folder</excludes>
   </configuration>
</plugin>
```
##### eclipse-checkstyle-plugin
add the following XML-code into the `.checkstyle`-file
```XML
<fileset-config file-format-version="1.2.0" simple-config="true" sync-formatter="false">
   <local-check-config ...>
      ...
   </local-check-config>
   <fileset ...>
      ...
   </fileset>

   <!-- exclude folder-->
   <filter name="FilesFromPackage" enabled="true">
      <filter-data value="path of the folder"/>
   </filter>
</fileset-config>

```