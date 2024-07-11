嗯 一个在当前时代没什么太多应用场景的小工具（毕竟这年头还有webapp的项目也不多了）

就是会在打包阶段统一移除webapp文件夹下面的js和jsp文件中的console.log输出语句（不包括error）

插件引入方式如下:
~~~xml
<build>
        <plugins>
            <plugin>
                <groupId>com.pumpkinor</groupId>
                <artifactId>rmconsole-maven-plugin</artifactId>
                <version>1.3</version>
                <executions>
                    <execution>
                        <id>rmConsoloLog</id>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>rmConsoloLog</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>rmConsoloLogAfter</id>
                        <phase>package</phase>
                        <goals>
                            <goal>rmConsoloLogAfter</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
</build>
~~~