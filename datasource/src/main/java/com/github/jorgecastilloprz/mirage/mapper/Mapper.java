package com.github.jorgecastilloprz.mirage.mapper;

import com.jorgecastilloprz.mirage.datasources.exceptions.MappingException;
import java.util.List;

/**
 * Decouples mappers to allow multiple type mapping in the app.
 *
 * @author jorge
 * @since 27/07/15
 */
public interface Mapper<Source, Dest> {

  Dest map(Source from) throws MappingException;

  List<Dest> map(List<Source> sourceList) throws MappingException;
}
