package com.universidad.refactoringu11;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class ArquitecturaPaquetesTest {

    @Test
    void losPaquetesNoDebenTenerDependenciasCiclicas() {
        JavaClasses clases = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.universidad.refactoringu11");
        slices().matching("com.universidad.refactoringu11.(*)..")
                .should().beFreeOfCycles()
                .check(clases);
    }
}
