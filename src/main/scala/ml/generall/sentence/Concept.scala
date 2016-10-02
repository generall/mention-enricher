// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package ml.generall.sentence



/** Concept link
  *
  * @param link
  *   Link to concept
  *   Required
  * @param weight
  *   Weight of concept link
  * @param hits
  *   Count of hits
  */
@SerialVersionUID(0L)
final case class Concept(
    link: String,
    weight: scala.Option[Double] = None,
    hits: scala.Option[Int] = None
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[Concept] with com.trueaccord.lenses.Updatable[Concept] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      __size += com.google.protobuf.CodedOutputStream.computeStringSize(1, link)
      if (weight.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeDoubleSize(2, weight.get) }
      if (hits.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, hits.get) }
      __size
    }
    final override def serializedSize: Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: com.google.protobuf.CodedOutputStream): Unit = {
      _output__.writeString(1, link)
      weight.foreach { __v =>
        _output__.writeDouble(2, __v)
      };
      hits.foreach { __v =>
        _output__.writeInt32(3, __v)
      };
    }
    def mergeFrom(`_input__`: com.google.protobuf.CodedInputStream): ml.generall.sentence.Concept = {
      var __link = this.link
      var __weight = this.weight
      var __hits = this.hits
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __link = _input__.readString()
          case 17 =>
            __weight = Some(_input__.readDouble())
          case 24 =>
            __hits = Some(_input__.readInt32())
          case tag => _input__.skipField(tag)
        }
      }
      ml.generall.sentence.Concept(
          link = __link,
          weight = __weight,
          hits = __hits
      )
    }
    def withLink(__v: String): Concept = copy(link = __v)
    def getWeight: Double = weight.getOrElse(0.0)
    def clearWeight: Concept = copy(weight = None)
    def withWeight(__v: Double): Concept = copy(weight = Some(__v))
    def getHits: Int = hits.getOrElse(0)
    def clearHits: Concept = copy(hits = None)
    def withHits(__v: Int): Concept = copy(hits = Some(__v))
    def getField(__field: com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = {
      __field.getNumber match {
        case 1 => link
        case 2 => weight.getOrElse(null)
        case 3 => hits.getOrElse(null)
      }
    }
    override def toString: String = com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = ml.generall.sentence.Concept
}

object Concept extends com.trueaccord.scalapb.GeneratedMessageCompanion[ml.generall.sentence.Concept] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[ml.generall.sentence.Concept] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): ml.generall.sentence.Concept = {
    require(__fieldsMap.keys.forall(_.getContainingType() == descriptor), "FieldDescriptor does not match message type.")
    val __fields = descriptor.getFields
    ml.generall.sentence.Concept(
      __fieldsMap(__fields.get(0)).asInstanceOf[String],
      __fieldsMap.get(__fields.get(1)).asInstanceOf[scala.Option[Double]],
      __fieldsMap.get(__fields.get(2)).asInstanceOf[scala.Option[Int]]
    )
  }
  def descriptor: com.google.protobuf.Descriptors.Descriptor = SentenceProto.descriptor.getMessageTypes.get(3)
  def messageCompanionForField(__field: com.google.protobuf.Descriptors.FieldDescriptor): com.trueaccord.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__field)
  def enumCompanionForField(__field: com.google.protobuf.Descriptors.FieldDescriptor): com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
  lazy val defaultInstance = ml.generall.sentence.Concept(
    link = ""
  )
  implicit class ConceptLens[UpperPB](_l: com.trueaccord.lenses.Lens[UpperPB, ml.generall.sentence.Concept]) extends com.trueaccord.lenses.ObjectLens[UpperPB, ml.generall.sentence.Concept](_l) {
    def link: com.trueaccord.lenses.Lens[UpperPB, String] = field(_.link)((c_, f_) => c_.copy(link = f_))
    def weight: com.trueaccord.lenses.Lens[UpperPB, Double] = field(_.getWeight)((c_, f_) => c_.copy(weight = Some(f_)))
    def optionalWeight: com.trueaccord.lenses.Lens[UpperPB, scala.Option[Double]] = field(_.weight)((c_, f_) => c_.copy(weight = f_))
    def hits: com.trueaccord.lenses.Lens[UpperPB, Int] = field(_.getHits)((c_, f_) => c_.copy(hits = Some(f_)))
    def optionalHits: com.trueaccord.lenses.Lens[UpperPB, scala.Option[Int]] = field(_.hits)((c_, f_) => c_.copy(hits = f_))
  }
  final val LINK_FIELD_NUMBER = 1
  final val WEIGHT_FIELD_NUMBER = 2
  final val HITS_FIELD_NUMBER = 3
}
