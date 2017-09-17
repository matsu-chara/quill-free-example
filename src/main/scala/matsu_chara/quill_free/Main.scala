package matsu_chara.quill_free

import com.typesafe.scalalogging.StrictLogging
import matsu_chara.quill_free.quill.RoleDb.{Master, Slave}
import matsu_chara.quill_free.quill.{MyDbContext, RoleDb}
import matsu_chara.quill_free.repo.{PersonFreeRepository, PersonRepository}

object Main extends StrictLogging {
  val personRepository = new PersonRepository

  def main(args: Array[String]): Unit = {
    implicit val roleDbMaster: RoleDb[RoleDb.Master] = new RoleDb[RoleDb.Master]("ctx")
    implicit val roleDbSlave: RoleDb[RoleDb.Slave] = new RoleDb[RoleDb.Slave]("ctx")

    import roleDbMaster._
    try {
      normal()
      free()
      freeRoleMaster()
      freeRoleSlave()
    } finally {
      roleDbMaster.close()
      roleDbSlave.close()
    }
  }

  def normal()(implicit ctx: MyDbContext): Unit = {
    val personOpt = ctx.transaction {
      personRepository.deleteAll()
      personRepository.insert(Person(id = 1, state = 0))
      personRepository.findById(1)
    }
    logger.info(s"normal result = $personOpt")
  }

  def free()(implicit ctx: MyDbContext): Unit = {
    val personFreeRepository = new PersonFreeRepository

    val freeOp = for {
      _ <- personFreeRepository.deleteAll()
      _ <- personFreeRepository.insert(Person(id = 1, state = 0))
      p <- personFreeRepository.findById(1)
    } yield p
    val personOpt = ctx.performIO(freeOp.transactional)
    logger.info(s"free result = $personOpt")
  }

  def freeRoleMaster()(implicit roleDb: RoleDb[Master]): Unit = {
    import roleDb._

    val personFreeRepository = new PersonFreeRepository
    val freeOp = for {
      _ <- personFreeRepository.deleteAll()
      _ <- personFreeRepository.insert(Person(id = 1, state = 0))
      p <- personFreeRepository.findById(1)
    } yield p
    val personOpt = roleDb.roleBasedPerformIO(freeOp.transactional)
    logger.info(s"freeRole result = $personOpt")
  }

  def freeRoleSlave()(implicit roleDb: RoleDb[Slave]): Unit = {
    import roleDb._

    val personFreeRepository = new PersonFreeRepository
    // cause compile error
//    val cantCompileOp = for {
//      _ <- personFreeRepository.deleteAll()
//      _ <- personFreeRepository.insert(Person(id = 1, state = 0))
//      p <- personFreeRepository.findById(1)
//    } yield p
//     roleDb.roleBasedPerformIO(cantCompileOp.transactional)

    val canCompileOp = for {
      p <- personFreeRepository.findById(1)
    } yield p
    val personOpt = roleDb.roleBasedPerformIO(canCompileOp)

    logger.info(s"freeRole result = $personOpt")
  }

}