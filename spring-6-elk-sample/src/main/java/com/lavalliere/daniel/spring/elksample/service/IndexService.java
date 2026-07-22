package com.lavalliere.daniel.spring.elksample.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.lavalliere.daniel.spring.elksample.ElkSampleApplication;
import com.lavalliere.daniel.spring.elksample.domain.IndexInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class IndexService {

    // private static Logger log = LoggerFactory.getLogger(IndexService.class);
    private final ElasticsearchClient client;

    private final ResourceLoader resourceLoader;

    private static IndexInfo getIndexInfo(BeanDefinition definition) {
        try {
            final Class<?> documentClass = Class.forName(definition.getBeanClassName());
            return new IndexInfo(
                getIndexName(documentClass),
                getIndexMappingPath(documentClass),
                getIndexSettingPath(documentClass)
            );
        } catch (ClassNotFoundException ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private static String getIndexName(final Class<?> documentClass) {
        final Document annotation = documentClass.getAnnotation(Document.class);
        return annotation.indexName();
    }

    private static String getIndexMappingPath(final Class<?> documentClass) {
        final Mapping annotation = documentClass.getAnnotation(Mapping.class);
        return annotation.mappingPath();
    }

    private static String getIndexSettingPath(final Class<?> documentClass) {
        final Setting annotation = documentClass.getAnnotation(Setting.class);
        return annotation.settingPath();
    }


    public IndexService(
        ElasticsearchClient client,
        ResourceLoader resourceLoader
    ) {
        this.client = client;
        this.resourceLoader = resourceLoader;
    }

    public void createIndices() {
        final List<IndexInfo> indexInfos = getIndexInformation();
        if (CollectionUtils.isEmpty(indexInfos)) return;

        for(final IndexInfo indexInfo : indexInfos) {
            deleteIndex(indexInfo);
            createIndex(indexInfo);
        }
    }

    private void deleteIndex(IndexInfo indexInfo) {
        try {
            final BooleanResponse exists = client.indices().exists(d -> d.index(indexInfo.name()));
            if (!exists.value()) return;

            client.indices().delete(d -> d.index(indexInfo.name()));
        } catch(Exception ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    // https://www.elastic.co/docs/manage-data/data-store/mapping
    // https://www.elastic.co/docs/reference/elasticsearch/index-settings
    private void createIndex(IndexInfo indexInfo) {
        try {
            client.indices()
                .create(c -> c
                        .index(indexInfo.name())
                        .mappings(t -> t.withJson(  // Loading our custom index mappings from personMapping.json
                            getMappings(indexInfo.mappingPath())
                        ))
                        .settings(s -> s.withJson(  // Loading our custom index settings from personSetting.json
                            getSettings(indexInfo.settingPath())
                        ))
                );
        } catch(Exception ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /*
       Using classpath scanning for candidate where the scanner will have an include filter
       which will be looking for a specific annotation
     */
    private List<IndexInfo> getIndexInformation() {
        final var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));
        final Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(ClassUtils.getPackageName(ElkSampleApplication.class));
        return beanDefinitions.stream().map(IndexService::getIndexInfo).toList();
    }

    private InputStream getMappings(final String mappingPath) {
        try {
            return resourceLoader.getResource("classpath:" + mappingPath).getInputStream();
        } catch (Exception ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private InputStream getSettings(final String settingPath) {
        try {
            return resourceLoader.getResource("classpath:" + settingPath).getInputStream();
        } catch (Exception ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
