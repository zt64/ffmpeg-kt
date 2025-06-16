package dev.zt64.ffmpegkt.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import java.io.OutputStream

class FfmpegProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return FunctionProcessor(
            environment.codeGenerator,
            environment.logger,
            environment.options
        )
    }
}

class FunctionProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("dev.zt64.ffmpegkt.ksp.annotation.Test")

        if (!symbols.iterator().hasNext()) return emptyList()

        symbols.forEach {
            it.accept(AutoBuilderVisitor(codeGenerator, logger, options, true), Unit)
        }
        // leave it blank for now
        return emptyList()
    }
}

class AutoBuilderVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>,
    private val flexible: Boolean
) : KSVisitorVoid() {
    private lateinit var file: OutputStream

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()
        val objectName =
            if (flexible) "mutable$className" else className.replaceFirstChar { c -> c.lowercase() }
        val fileName = "${className}Builder"
        val targetName = if (flexible) "Mutable$className" else className

        file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = packageName,
            fileName = fileName
        )

        file.write(
            """
            package $packageName
            
            public actual class $className {
            
            }
            """.trimIndent().toByteArray()
        )

        file.close()
    }
}