//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/jacky/GSoC/GeoGebraiOSARC/GeoGebraiOSARC/javasources/org/scilab/forge/jlatexmath/parser/ParserI.java
//

#ifndef _ParserI_H_
#define _ParserI_H_

#include "J2ObjC_header.h"
#include "Parser.h"

@protocol RXDocument;

@interface ParserI : NSObject < RXDocument >

#pragma mark Public

- (instancetype)init;

- (id<RXDocument>)parseWithId:(id)input;

- (void)setIgnoringCommentsWithBoolean:(jboolean)ignoreComments;

- (void)setIgnoringElementContentWhitespaceWithBoolean:(jboolean)whitespace;

@end

J2OBJC_EMPTY_STATIC_INIT(ParserI)

FOUNDATION_EXPORT void ParserI_init(ParserI *self);

FOUNDATION_EXPORT ParserI *new_ParserI_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ParserI)

typedef ParserI RXParserI;

#endif // _ParserI_H_
