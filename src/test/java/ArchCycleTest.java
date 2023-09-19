import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "de.flowpac.eta", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchCycleTest {

    @ArchTest
    static final ArchRule no_cycles_test =
            slices().matching("de.flowpac.eta.(*)..").should().beFreeOfCycles();

    @ArchTest
    static final ArchRule layered = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Domain").definedBy("..domain..")
            .layer("Exporter").definedBy("..exporter..")
            .layer("Adapter").definedBy("..adapter..")
            .whereLayer("Adapter").mayNotBeAccessedByAnyLayer()
            .whereLayer("Exporter").mayNotBeAccessedByAnyLayer()
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Exporter", "Adapter");

}

